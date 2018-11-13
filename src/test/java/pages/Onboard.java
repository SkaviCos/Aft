package pages;

import annotations.Page;
import annotations.StoreElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

@Page("Onboard")
public class Onboard extends PopUpPage {

    AppiumDriver driver;

    @StoreElement("Close")
    private WebElement closeButton;

    public Onboard(AppiumDriver driver, String user) {
        super(driver, user);
        this.driver = driver;
    }

    @Override
    protected WebElement getCloseButton() {
        return this.closeButton;
    }


}
