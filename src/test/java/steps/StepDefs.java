package steps;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CryptMainPage;
import pojo.MobileVerification;

import java.io.IOException;
import java.util.List;

import static com.testvagrant.stepdefs.core.Tapster.tapster;

public class StepDefs extends BaseSteps{


    @Given("^пользователь авторизуется под логином (.+) и паролем (.+)$")
    public void user_authorize_via_login_and_password(String login, String password) {
        CryptMainPage page = new CryptMainPage(getDriverInstanceFor("optimus"));
        page.authorize(login, password);
    }

    @Then("^(\\w+) decline iOS suggestions$")
    public void userDeclineIOSSuggestions(String consumer) {
        WebDriver driver = getDriverInstanceFor(consumer);
        List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeStaticText[starts-with(@name, 'Do you want to use your')]"));
        if (elements.size() > 0) {
            driver.findElement(By.xpath("//XCUIElementTypeButton[@name='No']")).click();
        }
    }

    @When("(\\w+) enters verification code for phone number (\\d+)")
    public void getVerificationCodeForSmsNumber(String consumer, String phoneNumber) throws OptimusException, NoSuchEventException, IOException {
        Response response = RestAssured.get("https://api-beta.crypterium.io/management/testers/mobile?mobile=" + phoneNumber);

        String value = response.as(MobileVerification.class).getCode();

        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen("SignupPage")
                .onElement("VerificationCode")
                .doAction("types")
                .withValue(value)
                .serve();
    }
}
