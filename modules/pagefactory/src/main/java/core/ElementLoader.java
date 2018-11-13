package core;

import annotations.Page;
import core.proxies.BlockElementProxyHandler;
import cucumber.api.java8.Pa;
import elements.BlockElement;
import elements.TypifiedElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import static utils.PageFactoryUtils.*;

public class ElementLoader {

   
//    @SuppressWarnings("unchecked")
//    public static <T> T create(Class<T> clazz, WebDriver driver) {
//        if (isBlockElement(clazz)) {
//            return (T) createBlockElement((Class<BlockElement>) clazz, driver);
//        }
//        if (isTypifiedElement(clazz)) {
//            return (T) createTypifiedElement((Class<TypifiedElement>) clazz, driver);
//        }
//        return createPageObject(clazz, driver);
//    }

   
    public static <T> void populate(T instance, WebDriver driver, String consumer) {
        if (isBlockElement(instance)) {
            populateBlockElement((BlockElement) instance, driver, consumer, ((BlockElement) instance).getName());
        } else {
            Page page = instance.getClass().getAnnotation(Page.class);
            // Otherwise consider instance as a page object
            populatePageObject(instance, driver, consumer, page.value());
        }
    }
    
//    public static <T extends BlockElement> T createBlockElement(Class<T> clazz, SearchContext searchContext) {
//        ElementLocator locator = new StoreElementLocatorFactory(searchContext).createLocator(clazz);
//        String elementName = getElementName(clazz);
//
//        InvocationHandler handler = new BlockElementProxyHandler(locator, elementName);
//        Class<?>[] interfaces = new Class[]{WebElement.class, WrapsElement.class, Locatable.class};
//        WebElement elementToWrap = (WebElement) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, handler);
//
//        return createBlockElement(clazz, elementToWrap, elementName);
//    }

    public static <T extends BlockElement> T createBlockElement(Class<T> elementClass, WebElement elementToWrap,
                                                              String name, String consumer) {
        try {
            T instance = newInstance(elementClass);
            instance.setWrappedElement(elementToWrap);
            instance.setName(name);
            // Recursively initialize elements of the block
            populatePageObject(instance, elementToWrap, consumer, name);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

//    @SuppressWarnings("unchecked")
//    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> clazz, SearchContext searchContext) {
//        ElementLocator locator = new StoreElementLocatorFactory(searchContext).createLocator(clazz);
//        String elementName = getElementName(clazz);
//        InvocationHandler handler = new BlockElementProxyHandler(locator, elementName);
//        Class<?>[] interfaces = new Class[]{WebElement.class, WrapsElement.class, Locatable.class};
//        WebElement elementToWrap = (WebElement) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, handler);
//
//        return createTypifiedElement(clazz, elementToWrap, elementName);
//    }

    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> elementClass, WebElement elementToWrap,
                                                                      String name) {
        try {
            T instance = newInstance(elementClass, elementToWrap);
            instance.setName(name);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static <T> T createPageObject(Class<T> clazz, WebDriver driver, String consumer) {
        try {
            T instance = newInstance(clazz, driver);
            Page annotation = clazz.getAnnotation(Page.class);
            if (annotation == null) {
                throw new RuntimeException(String.format("Cannot find page name for class %s", clazz.getName()));
            }
            populatePageObject(instance, driver, consumer, annotation.value());
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static void populateBlockElement(BlockElement BlockElement, SearchContext searchContext, String consumer, String name) {
        populateBlockElement(BlockElement, new StoreElementLocatorFactory(searchContext, name, consumer));
    }

    
    public static void populateBlockElement(BlockElement blockElement, StoreElementLocatorFactory locatorFactory) {
        @SuppressWarnings("unchecked")
        Class<BlockElement> elementClass = (Class<BlockElement>) blockElement.getClass();
        // Create locator that will handle Block annotation
        ElementLocator locator = locatorFactory.createLocator(elementClass);
        ClassLoader elementClassLoader = blockElement.getClass().getClassLoader();
        // Initialize block with WebElement proxy and set its name
        String elementName = getElementName(elementClass);

        InvocationHandler handler = new BlockElementProxyHandler(locator, elementName);
        Class<?>[] interfaces = new Class[]{WebElement.class, WrapsElement.class, Locatable.class};
        WebElement elementToWrap = (WebElement) Proxy.newProxyInstance(elementClassLoader, interfaces, handler);

        blockElement.setWrappedElement(elementToWrap);
        blockElement.setName(elementName);
        // Initialize elements of the block
        populatePageObject(blockElement, elementToWrap, locatorFactory.getConsumer(), elementName);
    }

    
    public static void populatePageObject(Object page, SearchContext searchContext, String consumer, String name) {
        populatePageObject(page, new StoreElementLocatorFactory(searchContext, name, consumer));
    }

    
    public static void populatePageObject(Object page, StoreElementLocatorFactory locatorFactory) {
        PageFactory.initElements(new CrypteriumFieldDecorator(locatorFactory), page);
    }

}
