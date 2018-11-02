package com.testvagrant.stepdefs.core;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.core.events.Event;
import com.testvagrant.stepdefs.core.events.Events;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Stash;

import java.io.IOException;
import java.util.List;

import static com.testvagrant.stepdefs.core.Tavern.tavern;
import static com.testvagrant.stepdefs.core.events.EventFinder.eventFinder;
import static com.testvagrant.stepdefs.core.events.EventLookup.eventLookup;
import static com.testvagrant.stepdefs.core.events.Events.ASSERT;
import static com.testvagrant.stepdefs.core.events.Events.NAVIGATION;
import static com.testvagrant.stepdefs.core.events.Events.SCROLL;
import static com.testvagrant.stepdefs.finder.OptimusElementFinder.optimusElementFinder;

public class Tapster {

    private AppiumDriver driver;
    private String consumer;
    private String screen;
    private String action;
    private String element;
    private String value;
    private int index;

    private Tapster() {
    }

    public static Tapster tapster() {
        return new Tapster();
    }

    public Tapster useDriver(AppiumDriver driver) {
        this.driver = driver;
        return this;
    }

    public Tapster asConsumer(String consumer) {
        this.consumer = consumer;
        return this;
    }

    public Tapster onScreen(String screen) {
        this.screen = StringUtils.capitalize(screen);
//        this.screen = screen;
        return this;
    }

    public Tapster doAction(String action) {
        this.action = action;
        return this;
    }

    public Tapster onElement(String element) {
        this.element = element;
        return this;
    }

    public Tapster withValue(String value) {
        this.value = value;
        return this;
    }

    public Tapster withIndex(int index) {
        this.index = index;
        return this;
    }

    public Tapster serve() throws NoSuchEventException, OptimusException, IOException {
        handlePopups();
        Event event = eventFinder().findEvent(action);
        Events events = eventLookup().load().getEvent(Integer.valueOf(event.getEventCode(), 2));
        if (ASSERT.equals(events) || SCROLL.equals(events)) {
            By targetBy = optimusElementFinder(driver).findBy(consumer, screen, element, value);
            tavern(driver).event(event).value(value).serve(targetBy);
        } else if (NAVIGATION.equals(events)) {
            tavern(driver).event(event).serveNavigation();
        } else {
            WebElement webElement = optimusElementFinder(driver).findWebElement(consumer, screen, element, value, index);
            tavern(driver).event(event).value(value).serve(webElement);
        }
        return this;
    }

    public void handlePopups() {
        while (true) {
            List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeAlert"));
            if (elements.size() > 0) {
                String name = elements.get(0).getAttribute("name");
                String answerName;
                switch (name) {
                    case "Use Face ID":
                    case "Use Touch ID":
                        String stashVal = Stash.getOrDefault(this.consumer, "TouchID", "false").toString();
                        answerName = Boolean.parseBoolean(stashVal) ? "Yes" : "No";
                        break;
                    default:
                        throw new RuntimeException(String.format("Undefined alert type: %s.", name));
                }
                driver.findElement(By.id(answerName)).click();
            } else {
                break;
            }
        }
    }

}