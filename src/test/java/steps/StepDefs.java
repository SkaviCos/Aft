package steps;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import pojo.MobileVerification;
import utils.Api;
import utils.CommonFunctions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class StepDefs extends BaseSteps {


    @Then("^(\\w+) decline iOS suggestions$")
    public void userDeclineIOSSuggestions(String consumer) {
        WebDriver driver = getDriverInstanceFor(consumer);
        List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeStaticText[starts-with(@name, 'Do you want to use your')]"));
        if (elements.size() > 0) {
            driver.findElement(By.xpath("//XCUIElementTypeButton[@name='No']")).click();
        }
    }

    @When("(\\w+) enters verification code for (phone number|email|<LoginType>) (.*)")
    public void getVerificationCodeForSmsNumber(String consumer, String type, String value) throws OptimusException, NoSuchEventException, IOException {

        try {
            String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);
            String endpoint = type.equalsIgnoreCase("email") ? "email" : "mobile";
            Response response = Api.management(String.format("/testers/%1$s?%1$s=%2$s", endpoint, formattedValue));
            String answer = response.as(MobileVerification.class).getCode();

            new GenericSteps().consumerOnScreenPerformsActionOnElementWithValue(consumer, "SignupPage", "types", "VerificationCode", answer);

        } catch (NoSuchElementException| TimeoutException e) {
            e.printStackTrace();
        }
    }

}
