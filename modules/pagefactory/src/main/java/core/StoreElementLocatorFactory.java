package core;

import annotations.StoreElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class StoreElementLocatorFactory implements ElementLocatorFactory {

    private final SearchContext searchContext;
    private String pageName;
    private String consumer;

    public StoreElementLocatorFactory(SearchContext context, String pageName, String consumer) {

        this.searchContext = context;
        this.pageName = pageName;
        this.consumer = consumer;

    }

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    /**
     * When a field on a class needs to be decorated with an {@link ElementLocator} this method will
     * be called.
     *
     * @param field the field
     * @return An ElementLocator object.
     */
    @Override
    public ElementLocator createLocator(Field field) {
        StoreElement annotation = field.getAnnotation(StoreElement.class);
        if (annotation == null) {
            return new DefaultElementLocator(this.searchContext, new Annotations(field));
        }
        return new DefaultElementLocator(this.searchContext, new StoreElementAnnotations(field, pageName, consumer));
    }

    public ElementLocator createLocator(Class clazz) {
        StoreElement annotation = (StoreElement) clazz.getAnnotation(StoreElement.class);
        return new DefaultElementLocator(this.searchContext, new StoreElementClassAnnotations(clazz, pageName, consumer));
    }
}
