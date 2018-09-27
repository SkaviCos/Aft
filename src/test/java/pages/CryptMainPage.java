package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CryptMainPage extends BasePage {

    AppiumDriver driver;


    @FindAll({
            @FindBy(xpath = "//android.widget.EditText[@resource-id='com.crypterium.beta:id/password']"),
            @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Login']")
    })
    private WebElement loginTextInput;

    @FindAll({
            @FindBy(xpath = "//android.widget.EditText[@resource-id='com.crypterium.beta:id/password']"),
            @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Password\"]")
    })
    private WebElement passwordTextInput;

    @FindAll({
            @FindBy(xpath = "//android.widget.Button[@resource-id='com.crypterium.beta:id/signIn']"),
            @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Login\"]")

    })
    private WebElement buttonSignIn;

    public CryptMainPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void authorize(String login, String password) {
        loginTextInput.click();
        loginTextInput.sendKeys(login);
        loginTextInput.click();
        passwordTextInput.sendKeys(password);
        buttonSignIn.click();

    }



}
