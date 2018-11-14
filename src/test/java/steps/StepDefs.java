package steps;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import finder.OptimusElementFinder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AuthPage;
import pages.Faq;
import pages.Onboard;
import pojo.FeatureAccess;
import pojo.MobileVerification;
import utils.Api;
import utils.CommonFunctions;
import utils.Stash;

import java.io.IOException;
import java.util.List;

public class StepDefs extends BaseSteps {


    @Then("^(\\w+) checks alert$")
    public void userChecksAlert(String consumer) {
        try {
            AppiumDriver driver = getDriverInstanceFor(consumer);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement elements = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeAlert")));
            String name = elements.getAttribute("name");
            String answerName;
            String stashVal;
            switch (name) {
                case "Use Face ID":
                case "Use Touch ID":
                    stashVal = Stash.getOrDefault(consumer, "TouchID", "false").toString();
                    answerName = Boolean.parseBoolean(stashVal) ? "Yes" : "No";
                    break;
                case "“Beta” Would Like to Send You Notifications":
                    stashVal = Stash.getOrDefault(consumer, "Allow Notifications", "false").toString();
                    answerName = Boolean.parseBoolean(stashVal) ? "Allow" : "Don’t Allow";
                    break;
                default:
                    throw new RuntimeException(String.format("Undefined alert type: %s.", name));
            }
            driver.findElement(By.id(answerName)).click();
        } catch (TimeoutException e) {

        }
    }

    @When("(\\w+) enters verification code for (phone number|email|<LoginType>) (.*)")
    public void getVerificationCodeForSmsNumber(String consumer, String type, String value) throws OptimusException, NoSuchEventException, IOException {
        try {
            new WebDriverWait(getDriverInstanceFor(consumer), 3)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id("LoginInput")));

            By pinElementBy = OptimusElementFinder.optimusElementFinder(getDriverInstanceFor(consumer)).findBy(consumer, "PinPage", "PinInput", "");
            List<? extends WebElement> elements = getDriverInstanceFor(consumer).findElements(pinElementBy);
            if (!elements.isEmpty()) {
                return;
            }

            new GenericSteps().assertelement(consumer, "SignupPage", "VerificationCode", "displayed");
            String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);
            String endpoint = type.equalsIgnoreCase("email") ? "email" : "mobile";
            Response response = Api.management(String.format("/testers/%1$s?%1$s=%2$s", endpoint, formattedValue));
            String answer = response.as(MobileVerification.class).getCode();

            new GenericSteps().consumerOnScreenPerformsActionOnElementWithValue(consumer, "SignupPage", "types", "VerificationCode", answer);

        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @When("(\\w+) checks features access")
    public void checkFeatureAccess(String consumer, List<FeatureAccess> features) throws OptimusException, NoSuchEventException, IOException {
        GenericSteps steps = new GenericSteps();
        for (FeatureAccess featureAccess : features) {
            steps.consumerOnScreenPerformsActionOnElement(consumer, "MainPage", "scroll to", featureAccess.getName());
            steps.consumerOnScreenPerformsActionOnElement(consumer, "MainPage", "taps", featureAccess.getName());
            new Onboard(getDriverInstanceFor(consumer), consumer).tryClose();
            new Faq(getDriverInstanceFor(consumer), consumer).tryClose();

            if (featureAccess.isEnabled()) {
                steps.assertelement(consumer, featureAccess.getName().equalsIgnoreCase("ByyCRPTForCrypto") ? "Exchange" : featureAccess.getName(), "PageTitle", "displayed");
                steps.consumerOnScreenPerformsActionOnElement(consumer, featureAccess.getName().equalsIgnoreCase("ByyCRPTForCrypto") ? "Exchange" : featureAccess.getName(), "taps", "Back");
            } else {
                if (featureAccess.isBeta()) {
                    steps.assertelement(consumer, "MainPage", "BetaAlertConfirm", "displayed");
                } else {
                    steps.assertelement(consumer, "MainPage", "KycAlert", "displayed");
                }
                steps.consumerOnScreenPerformsActionOnElement(consumer, "MainPage", "taps", "AlertClose");
            }
        }
    }

    @When("^test$")
    public void test() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        AuthPage page = new AuthPage(getDriverInstanceFor("user"), "user");
        page.authorize("123", "123");
    }
}
