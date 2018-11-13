package pages;

import annotations.Action;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public abstract class PopUpPage extends BasePage {

    public PopUpPage(AppiumDriver driver, String user) {
        super(driver, user);
    }

    abstract protected WebElement getCloseButton();

    @Action("try close")
    public void tryClose() {
        try {
            this.tap(getCloseButton());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}