package steps;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pojo.MobileVerification;
import utils.Api;
import utils.CommonFunctions;

import java.io.IOException;
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

    @When("(\\w+) enters verification code for phone number (.*)")
    public void getVerificationCodeForSmsNumber(String consumer, String phoneNumber) throws OptimusException, NoSuchEventException, IOException {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, phoneNumber);
        Response response = Api.management("/testers/mobile?mobile=" + formattedValue);
        String value = response.as(MobileVerification.class).getCode();

        new GenericSteps().consumerOnScreenPerformsActionOnElementWithValue(consumer, "SignupPage", "types", "VerificationCode", value);

    }

}
