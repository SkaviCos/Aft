package pages;

import annotations.Page;
import annotations.StoreElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

@Page("FAQ")
public class Faq extends PopUpPage {

    AppiumDriver driver;

    @StoreElement("Close")
    private WebElement closeButton;

    public Faq(AppiumDriver driver, String user) {
        super(driver, user);
        this.driver = driver;
    }

    @Override
    protected WebElement getCloseButton() {
        return this.closeButton;
    }
    

}
