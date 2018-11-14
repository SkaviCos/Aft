package pages.activities;

import annotations.Page;
import annotations.StoreElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

@Page("SendCrypto")
public class SendCryptoPage extends AnyActivityPage {


    @StoreElement("Back")
    private WebElement backBtn;

    public SendCryptoPage(AppiumDriver driver, String user) {
        super(driver, user);
    }






}
