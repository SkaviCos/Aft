package helpers;

import finder.WaitControl;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

/**
 * Created by abhishek on 19/06/17.
 */
public abstract class AssertHelper extends ActionHelper {

    AssertHelper(AppiumDriver driver) {
        super(driver);
    }

    public static AssertHelper assertHelper(AppiumDriver driver) {
        if (driver.getCapabilities().getCapability("platformName").toString().equalsIgnoreCase("android")) {
            return new AndroidAssertHelper(driver);
        } else {
            return new IosAssertHelper(driver);
        }
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

    abstract public void isDisplayedOnPage(By by);

    abstract public void isNotDisplayedOnPage(By by);

    public static class AndroidAssertHelper extends AssertHelper {

        AndroidAssertHelper(AppiumDriver driver) {
            super(driver);
        }

        @Override
        public void isDisplayedOnPage(By by) {
            ScrollHelper.scroller(this.driver).scrollTo(by);
            Assert.assertTrue("Element not visible", isElementPresent(by));

        }

        @Override
        public void isNotDisplayedOnPage(By by) {
            ScrollHelper.scroller(this.driver).scrollTo(by);
            Assert.assertFalse("Element is Visible", isElementPresent(by));
        }
    }

    public static class IosAssertHelper extends AssertHelper {

        IosAssertHelper(AppiumDriver driver) {
            super(driver);
        }

        @Override
        public void isDisplayedOnPage(By by) {
            new WaitControl(driver).waitFor("presence", by);
            Assert.assertTrue("Element not visible", isElementPresent(by));
        }

        @Override
        public void isNotDisplayedOnPage(By by) {
            new WaitControl(driver).waitFor("inVisibility", by);
            Assert.assertFalse("Element is Visible", isElementPresent(by));
        }
    }
}
