package pages;

import annotations.Action;
import annotations.Page;
import com.testvagrant.commons.exceptions.OptimusException;
import finder.OptimusElementFinder;
import helpers.ScrollHelper;
import helpers.TapHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.StepDefs;

import java.io.IOException;

@Page(Wallet.WALLET_PAGE_NAME)
public class Wallet extends BasePage {

    public static final String WALLET_PAGE_NAME = "MainPage";

    public Wallet(AppiumDriver driver, String user) {
        super(driver, user);
    }

    @Action("open activity")
    public void openActivity(String activityName) throws IOException, OptimusException {
        By elementBy = OptimusElementFinder.optimusElementFinder(this.driver).findBy(this.user, WALLET_PAGE_NAME, activityName, "");
        ScrollHelper.scroller(driver).scrollTo(elementBy);
        TapHelper.tapHelper(driver).tap(driver.findElement(elementBy));
        new Onboard(driver, user).tryClose();
        new Faq(driver, user).tryClose();
    }


}
