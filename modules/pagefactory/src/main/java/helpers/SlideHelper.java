package helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class SlideHelper extends ActionHelper {

    private final int offset = 200;
    private WebElement element;

    private SlideHelper(AppiumDriver driver) {
        super(driver);
    }


    public static SlideHelper slider(AppiumDriver driver) {
        return new SlideHelper(driver);
    }


    public SlideHelper on(WebElement element) {
        this.element = element;
        return this;
    }

    public void slide(String percentage) {
        int xStartingPoint = element.getLocation().getX();
        int xEndingPoint = element.getSize().getWidth();
        int yStartingAndEndingPoint = element.getLocation().getY();
        moveSliderAccordingToSpecifiedPercentage(xStartingPoint, xEndingPoint, yStartingAndEndingPoint, getInt(percentage));
    }

    private void moveSliderAccordingToSpecifiedPercentage(int xStartingPoint, int xEndingPoint, int yStartingAndEndingPoint, int slideByPercentage) {
        double slideFactor = (double) slideByPercentage / (double) 100;
        int pointToMoveTo = (int) ((xEndingPoint + offset) * slideFactor);
//        touchAction = new TouchAction(driver);
        waitForElementToBeClickable(element);
        new TouchAction(driver).longPress(LongPressOptions.longPressOptions()
                    .withElement(ElementOption.element(element))
                    .withPosition(PointOption.point(xStartingPoint, yStartingAndEndingPoint))
                    .withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(pointToMoveTo, yStartingAndEndingPoint))
                .release().perform();
    }


}
