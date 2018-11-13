package helpers;

import finder.WaitControl;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

/**
 * Created by abhishek on 19/06/17.
 */
public class AssertHelper extends ActionHelper {

    AssertHelper(AppiumDriver driver) {
        super(driver);
    }

    public static AssertHelper assertHelper(AppiumDriver driver) {
        return new AssertHelper(driver);
    }

    public void isTextDisplayed(By by, String text) {
        new WaitControl(driver).waitFor("presence", by);

        if (text.contains("empty") || text.contains("null"))
            Assert.assertEquals("Text is not empty", "", driver.findElement(by).getText());
        else
            Assert.assertEquals("Text not present ", text, driver.findElement(by).getText());
    }

    public void isTextNotDisplayed(By by, String text) {
        new WaitControl(driver).waitFor("presence", by);
        Assert.assertNotEquals("Text is present ", text, driver.findElement(by).getText());
    }

    public void isEnabled(By by) {
        new WaitControl(driver).waitFor("presence", by);
        Assert.assertTrue("Element not enabled ", driver.findElement(by).isEnabled());
    }

    public void isNotEnabled(By by) {
        new WaitControl(driver).waitFor("presence", by);
        Assert.assertFalse("Element is enabled ", by.findElement(driver).isEnabled());
    }

    public void isDisplayed(By by) {
        new WaitControl(driver).waitFor("presence", by);
        Assert.assertTrue("Element not visible", isElementPresent(by));
    }

    public void isNotDisplayed(By by) {
        new WaitControl(driver).waitFor("inVisibility", by);
        Assert.assertFalse("Element is Visible", isElementPresent(by));
    }
}
