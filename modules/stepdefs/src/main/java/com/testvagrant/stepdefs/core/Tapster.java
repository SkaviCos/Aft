package com.testvagrant.stepdefs.core;

import annotations.Action;
import annotations.Page;
import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.core.events.Event;
import com.testvagrant.stepdefs.core.events.Events;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;
import pages.BasePage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.testvagrant.stepdefs.core.Tavern.tavern;
import static com.testvagrant.stepdefs.core.events.EventFinder.eventFinder;
import static com.testvagrant.stepdefs.core.events.EventLookup.eventLookup;
import static com.testvagrant.stepdefs.core.events.Events.ASSERT;
import static com.testvagrant.stepdefs.core.events.Events.NAVIGATION;
import static com.testvagrant.stepdefs.core.events.Events.SCROLL;
import static finder.OptimusElementFinder.optimusElementFinder;

public class Tapster {

    private AppiumDriver driver;
    private String consumer;
    private String screen;
    private String action;
    private String element;
    private String value;
    private String[] values;
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

    public Tapster withValues(String... values) {
        this.values = values;
        return this;
    }

    @SuppressWarnings("all")
    public Tapster serve() throws NoSuchEventException, OptimusException, IOException {

        Reflections reflections = new Reflections("pages");
        Method method = null;

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Page.class);
        Class<? extends BasePage> page = (Class<? extends BasePage>) reflections.getTypesAnnotatedWith(Page.class).stream()
                .filter(BasePage.class::isAssignableFrom)
                .filter(c -> c.getAnnotation(Page.class).value().equalsIgnoreCase(this.screen))
                .findFirst().orElse(null);

        if (page != null) {
            List<Method> methods = new ArrayList<>();
            Class<?> clazz = page;
            while (clazz != Object.class) {
                methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
                clazz = clazz.getSuperclass();
            }
            method = methods.stream().filter(m -> m.isAnnotationPresent(Action.class))
                    .filter(m -> m.getAnnotation(Action.class).value().equalsIgnoreCase(this.action)).findFirst().orElse(null);
        }
        if (page != null && method != null) {
            try {
                BasePage crntPage = page.getConstructor(AppiumDriver.class, String.class).newInstance(this.driver, this.consumer);
                if(values == null && value == null) {
                    method.invoke(crntPage);
                } else {
                    if (values.length == 0 && value != null) {
                        values[0] = value;
                    }
                    method.invoke(crntPage, values);
                }

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Cannot create instance of page " + this.screen, e);
            }
        } else {
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
        }
        return this;
    }

}