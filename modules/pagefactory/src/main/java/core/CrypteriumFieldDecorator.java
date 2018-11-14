package core;

import core.proxies.BlockElementProxyHandler;
import elements.BlockElement;
import elements.TypifiedElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

import static core.ElementLoader.createBlockElement;
import static core.ElementLoader.createTypifiedElement;
import static utils.PageFactoryUtils.*;

public class CrypteriumFieldDecorator implements FieldDecorator {

    protected StoreElementLocatorFactory factory;
    protected String consumer;

    public CrypteriumFieldDecorator(StoreElementLocatorFactory factory) {
        this.factory = factory;
        consumer = factory.getConsumer();
    }

    public Object decorate(ClassLoader loader, Field field) {

        try {
            if (isTypifiedElement(field)) {
                return decorateTypifiedElement(loader, field);
            }
            if (isBlockElement(field)) {
                return decorateHtmlElement(loader, field);
            }
            if (isWebElement(field) && !field.getName().equals("wrappedElement")) {
                return decorateWebElement(loader, field);
            }
//            if (isTypifiedElementList(field)) {
//                return decorateTypifiedElementList(loader, field);
//            }
//            if (isBlockElement(field)) {
//                return decorateHtmlElementList(loader, field);
//            }
//            if (isWebElementList(field)) {
//                return decorateWebElementList(loader, field);
//            }
            return null;
        } catch (ClassCastException ignore) {
            return null; // See bug #94 and NonElementFieldsTest
        }
    }

    protected <T extends TypifiedElement> T decorateTypifiedElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);
        //noinspection unchecked
        return createTypifiedElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    protected <T extends BlockElement> T decorateHtmlElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);

        //noinspection unchecked
        return createBlockElement((Class<T>) field.getType(), elementToWrap, consumer, getElementName(field));
    }

    protected WebElement decorateWebElement(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new BlockElementProxyHandler(locator, getElementName(field));

        Class<?>[] interfaces = new Class[]{WebElement.class, WrapsElement.class, Locatable.class};
        return (WebElement) Proxy.newProxyInstance(loader, interfaces, handler);
    }

//    protected <T extends TypifiedElement> List<T> decorateTypifiedElementList(ClassLoader loader, Field field) {
//        @SuppressWarnings("unchecked")
//        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
//        ElementLocator locator = factory.createLocator(field);
//        String name = getElementName(field);
//
//        InvocationHandler handler = new TypifiedElementListNamedProxyHandler<>(elementClass, locator, name);
//
//        return createTypifiedElementListProxy(loader, handler);
//    }
//
//    protected <T extends HtmlElement> List<T> decorateHtmlElementList(ClassLoader loader, Field field) {
//        @SuppressWarnings("unchecked")
//        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
//        ElementLocator locator = factory.createLocator(field);
//        String name = getElementName(field);
//
//        InvocationHandler handler = new HtmlElementListNamedProxyHandler<>(elementClass, locator, name);
//
//        return createHtmlElementListProxy(loader, handler);
//    }
//
//    protected List<WebElement> decorateWebElementList(ClassLoader loader, Field field) {
//        ElementLocator locator = factory.createLocator(field);
//        InvocationHandler handler = new WebElementListNamedProxyHandler(locator, getElementName(field));
//
//        return createWebElementListProxy(loader, handler);
//    }
}
