package pages;

import annotations.Page;
import helpers.ScrollHelper;
import helpers.TapHelper;
import helpers.TypeHelper;
import core.CrypteriumFieldDecorator;
import core.StoreElementLocatorFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;


public class BasePage {

    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected String user;

    public BasePage(AppiumDriver driver, String user) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 30);
        this.user = user;
        String pageName = this.getClass().getAnnotation(Page.class).value();
        PageFactory.initElements(new CrypteriumFieldDecorator(new StoreElementLocatorFactory(driver, pageName, user)), this);

    }

    private static ExpectedCondition<WebElement> elementToBeChecked(
            final WebElement element) {
        return new ExpectedCondition<WebElement>() {

            public ExpectedCondition<WebElement> visibilityOfElement =
                    ExpectedConditions.visibilityOf(element);

            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = visibilityOfElement.apply(driver);
                try {
                    if (element != null && element.getAttribute("checked").equals("true")) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be checked : " + element;
            }
        };
    }

    public boolean allowPermissionPopup() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        try {
            By allowXpath = By.xpath("//*[@text='Allow']");
            WebElement acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
            acceptElement.click();
            acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
            acceptElement.click();
            return true;
        } catch (TimeoutException e) {
        }
        return false;
    }

    //
    public void waitForElementToBeVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForElementToBeVisible(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {

        }
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeSelected(WebElement element) {
        wait.until(ExpectedConditions.elementSelectionStateToBe(element, true));
    }

    public void waitForElementToBeRefreshed(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public void waitForElementToBeRefreshed(By by) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
    }

    public void waitForElementToBeClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForPresenceOfElement(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForPresenceOfAllElements(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public boolean waitForTextToBePresentInElement(By by, String text) {
        try {
            if (wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text)))
                return true;
        } catch (TimeoutException e) {
            return false;
        }
        return false;
    }

    public boolean waitForTextToBeNonEmpty(final By by) {
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(by).getText().length() != 0;
            }
        });
        return false;
    }

    public void waitForInvisibilityOfElementByText(By by, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementWithText(by, text));
    }

    public void waitForElementToBeInvisible(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementToBeChecked(WebElement element) {
        wait.until(elementToBeChecked(element));
    }


    public void tap(WebElement element) {
        TapHelper.tapHelper(this.driver).tap(element);
    }

    public void sendKeys(WebElement elem, String text) {
        TypeHelper.typeHelper(this.driver).onElement(elem).type(text);
    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (WebDriverException e) {
        }
    }

//    public void scrollTo(String text) {
//        try {
//            riderDriver.scrollTo(text);
//        } catch (WebDriverException e) {
//
//        }
//    }

    public void scrollTo(String text) {
        scrollDownTo(text);
    }

    public void scrollDownTo(String text) {
        scrollDownTo(By.xpath("//*[@text=\"" + text + "\"]"));
    }

    public void scrollDownTo(By byOfElementToBeFound) {
        ScrollHelper.scroller(this.driver).scrollTo(byOfElementToBeFound);
    }

    public void scrollDown() {
        ScrollHelper.scroller(this.driver).down();
    }

    public void scrollUp() {
        ScrollHelper.scroller(this.driver).up();
    }

    public void swipeLeftToRight() {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        new TouchAction(driver).press(PointOption.point(width / 3, height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width * 2 / 3, height / 2))
                .release().perform();
    }

    public void swipeRightToLeft() {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        new TouchAction(driver).press(PointOption.point(width * 9 / 10, height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 10, height / 2))
                .release().perform();
    }

    public void scrollUpTo(String text) {
        scrollUpTo(By.xpath("//*[@text=\"" + text + "\"]"));
    }

    public void scrollUpTo(By by) {
        hideKeyboard();
        int i = 0;
        while (i < 5) {
            if (driver.findElements(by).size() > 0)
                return;

            scrollUp();

            i++;
        }
        Assert.fail("Did not find : " + by.toString());
    }

    public void clickOnListUsingIndex(By by, int index) {
        waitForPresenceOfAllElements(by);
        List<WebElement> locationNames = driver.findElements(by);
        locationNames.get(index).click();
    }

    public void scrollDownToByAndTap(By by) {
        scrollDownTo(by);
        driver.findElement(by).click();
    }


    public void swipeRightToLeftToFindElementAndClick(By byOfElementToSwipeOn, By byOfElementToBeFound) {

        int height = driver.findElement(byOfElementToSwipeOn).getLocation().getY() + 50;
        int width = driver.manage().window().getSize().getWidth();

        System.out.println("Screen width ::" + width);


        int count = 0;
        while (count < 20) {
            if (driver.findElements(byOfElementToBeFound).size() > 0) {
                driver.findElement(byOfElementToBeFound).click();
                return;
            }
            new TouchAction(driver).press(PointOption.point(width * 6 / 7, height))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 7, height))
                    .release().perform();
            count++;
        }
        Assert.fail("Could not find element with by - " + byOfElementToBeFound.toString());
    }

    public void swipeRtoLOnElementUsingCount(By by, int count) {

        Point p = driver.findElement(by).getLocation();
        System.out.println(by);
        int x_int = ((Integer) p.getX());
        int y_int = ((Integer) p.getY());


        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        System.out.println("x_int :: " + x_int);
        System.out.println("Screen width ::" + width);


        boolean flag = true;
        int _count = 0;
        while (flag) {
            try {
                if (_count == count) {
                    flag = false;
                } else {
                    new TouchAction(driver).press(PointOption.point(width - x_int, y_int))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(x_int, y_int))
                            .release().perform();

                    _count++;
                    System.out.println("Swipe Count :: " + _count);
                }
            } catch (Exception e) {
                new TouchAction(driver).press(PointOption.point(width - 100, y_int + 100))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(x_int + 100, y_int + 100))
                        .release().perform();

                _count++;
                System.out.println("Inside catch block");

            }
        }
    }


    public void clickBy(By by) {
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
    }

    protected void swipeFromTo(WebElement startElement, WebElement stopElement) {
        new TouchAction(driver).press(PointOption.point(startElement.getLocation().getX(), startElement.getLocation().getY()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(stopElement.getLocation().getX(), stopElement.getLocation().getY()))
                .release().perform();

    }

    public void swipeFromLeftToRight(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        int xAxisStartPoint = webElement.getLocation().getX();
        int xAxisEndPoint = xAxisStartPoint + webElement.getSize().getWidth();
        int yAxis = webElement.getLocation().getY();
        TouchAction act = new TouchAction(driver);
        System.out.print(xAxisStartPoint + " " + yAxis);
        act.longPress(PointOption.point(xAxisStartPoint, yAxis)).moveTo(PointOption.point(xAxisEndPoint - 1, yAxis)).release().perform();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean clickAny(By... bys) {
        for (By by : bys)
            if (!driver.findElements(by).isEmpty()) {
                driver.findElement(by).click();
                waitForElementToBeInvisible(by);
                return true;
            }
        return false;
    }
}
