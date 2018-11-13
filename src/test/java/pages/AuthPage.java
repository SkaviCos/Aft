package pages;

import annotations.Action;
import annotations.Page;
import annotations.StoreElement;
import helpers.TapHelper;
import helpers.TypeHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

@Page("AuthPage")
public class AuthPage extends BasePage {

    AppiumDriver driver;

    @StoreElement("LoginInput")
    private WebElement loginTextInput;

    @StoreElement("PasswordInput")
    private WebElement passwordTextInput;

    @StoreElement("SubmitButton")
    private WebElement buttonSignIn;

    @StoreElement("SignUp")
    private WebElement signUpButton;

    public AuthPage(AppiumDriver driver, String user) {
        super(driver, user);
        this.driver = driver;
    }

    @Action("authorizes")
    public void authorize(String login, String password) {
        TypeHelper.typeHelper(this.driver).onElement(this.loginTextInput).type(login);
//        this.sendKeys(this.loginTextInput, login);
        TypeHelper.typeHelper(this.driver).onElement(this.passwordTextInput).type(password);
//        this.sendKeys(this.passwordTextInput, password);
        TapHelper.tapHelper(this.driver).tap(this.buttonSignIn);
//        this.buttonSignIn.click();
    }

}
