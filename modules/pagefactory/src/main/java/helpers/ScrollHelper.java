package helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;

import java.time.Duration;


public abstract class ScrollHelper extends ActionHelper {

    protected ScrollHelper(AppiumDriver driver) {
        super(driver);
    }

    public static ScrollHelper scroller(AppiumDriver driver) {
        if (driver.getCapabilities().getCapability("platformName").toString().equalsIgnoreCase("android")) {
            return new AndroidScrollHelper(driver);
        } else {
            return new IOSScrollHelper(driver);
        }
    }

    public void scrollDown(By by) {
        hideKeyboard();
        int i = 0;
        while (i < 12) {
            if (isElementVisible(by)) {
                return;
            }
            down();
            i++;
        }
    }

    public void scrollUp(By by) {
        hideKeyboard();
        int i = 0;
        while (i < 12) {
            if (isElementPresent(by)) {
                return;
            }
            up();
            i++;
        }
    }

    public void scrollUpInElement(By inElement, String toText) {
        hideKeyboard();
        WebElement element = inElement.findElement(driver);
        Dimension dimension = element.getSize();
        Point point = element.getLocation();

        Point start = new Point(point.getX() + dimension.getHeight() * 2 / 3
                , point.getY() + dimension.getWidth() / 2);
        Point end = new Point(point.getX() + dimension.getHeight() / 3
                , point.getY() + dimension.getWidth() / 2);

        int i = 0;
        while (i < 12) {
            if (isTextPresent(inElement, toText)) {
                return;
            }
            swipe(start.x, start.y, end.x, end.y);
            i++;
        }
    }

    public void scrollDownInElement(By inElement, String toText) {
        hideKeyboard();
        WebElement element = inElement.findElement(driver);
        Dimension dimension = element.getSize();
        Point point = element.getLocation();

        Point start = new Point(point.getX() + dimension.getHeight() / 3
                , point.getY() + dimension.getWidth() / 2);
        Point end = new Point(point.getX() + dimension.getHeight() * 2 / 3
                , point.getY() + dimension.getWidth() / 2);

        int i = 0;
        while (i < 12) {
            if (isTextPresent(inElement, toText)) {
                return;
            }
            swipe(start.x, start.y, end.x, end.y);
            i++;
        }
    }

    public void scrollRightInElement(By inElement, String toText) {
        hideKeyboard();
        WebElement element = inElement.findElement(driver);
        Dimension dimension = element.getSize();
        Point point = element.getLocation();

        Point start = new Point(point.getX() + dimension.getHeight() / 2
                , point.getY() + dimension.getWidth() * 2 / 3);
        Point end = new Point(point.getX() + dimension.getHeight() / 2
                , point.getY() + dimension.getWidth() / 3);

        int i = 0;
        while (i < 12) {
            if (isTextPresent(inElement, toText)) {
                return;
            }
            swipe(start.x, start.y, end.x, end.y);
            i++;
        }
    }

    public void scrollLeftinElement(By inElement, String toText) {
        hideKeyboard();
        WebElement element = inElement.findElement(driver);
        Dimension dimension = element.getSize();
        Point point = element.getLocation();

        Point start = new Point(point.getX() + dimension.getHeight() / 2
                , point.getY() + dimension.getWidth() / 3);
        Point end = new Point(point.getX() + dimension.getHeight() / 2
                , point.getY() + dimension.getWidth() * 2 / 3);

        int i = 0;
        while (i < 12) {
            if (isTextPresent(inElement, toText)) {
                return;
            }
            swipe(start.x, start.y, end.x, end.y);
            i++;
        }
    }

    abstract public void scrollTo(By by);

    public void down() {
//        driver.swipe(5, getHeight() * 2 / 3, 5, getHeight() / 3, 1000);
        new TouchAction(driver).press(PointOption.point(5, getHeight() * 3 / 4))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(5, getHeight() / 4))
                .release().perform();
    }

    public void up() {
//        driver.swipe(5, getHeight() / 3, 5, getHeight() * 2 / 3, 1000);
        new TouchAction(driver).press(PointOption.point(5, getHeight() / 4))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(5, getHeight() * 3 / 4))
                .release().perform();

    }

    private void swipe(int startX, int startY, int endX, int endY) {
//        driver.swipe(startX, startY, endX, endY, 1000);
        new TouchAction(driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();

    }

    private boolean isTextPresent(By by, String text) {
        try {
            String xpath = "//*[@text='" + text + "']";
            By.xpath(xpath).findElement(driver.findElement(by));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static class AndroidScrollHelper extends ScrollHelper {

        protected AndroidScrollHelper(AppiumDriver driver) {
            super(driver);
        }

        @Override
        public void scrollTo(By by) {
            scrollDown(by);
        }

    }

    public static class IOSScrollHelper extends ScrollHelper {

        protected IOSScrollHelper(AppiumDriver driver) {
            super(driver);
        }

        @Override
        public void scrollTo(By by) {
            int i = 0;
            while (i < 12) {
                WebElement e = driver.findElement(by);
                int xEl = e.getLocation().getX();
                int yEl = e.getLocation().getY();
                // element is on right
                if (xEl > getWidth()) {
                    //right
                    new TouchAction(driver).press(PointOption.point(getWidth() * 3 / 4, 10))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(getWidth() / 4, 10))
                            .release().perform();
                } else if (xEl < 0) {
                    //left
                    new TouchAction(driver).press(PointOption.point(getWidth() / 4, 10))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(getWidth() * 3 / 4, 10))
                            .release().perform();
                }
                if (yEl > getHeight()) {
                    down();
                } else if (yEl < 0) {
                    up();
                }
                if (e.isDisplayed()) {
                    break;
                }
                i++;
            }
        }
    }

}
