package core;

import annotations.StoreElement;
import com.testvagrant.commons.exceptions.OptimusException;
import finder.OptimusElementFinder;
import elements.BlockElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

import java.io.IOException;

public class StoreElementClassAnnotations<T extends BlockElement> extends AbstractAnnotations {

    private Class<T> elementClass;
    private String pageName;
    private String user;

    /**
     * @param elementClass expected to be an element in a Page Object
     */
    public StoreElementClassAnnotations(Class<T> elementClass, String pageName, String user) {
        this.elementClass = elementClass;
        this.pageName = pageName;
        this.user = user;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Looks for one of {@link FindBy},
     * {@link FindBys} or
     * {@link FindAll} field annotations. In case
     * no annotations provided for field, uses field name as 'id' or 'name'.
     *
     * @throws IllegalArgumentException when more than one annotation on a field provided
     */
    @Override
    public By buildBy() {
        Class<?> clazz = elementClass;
        while (clazz != Object.class) {
            if (clazz.isAnnotationPresent(StoreElement.class)) {
                StoreElement storeElement = clazz.getAnnotation(StoreElement.class);
                String elementName = storeElement.value();
                String param = storeElement.param();
                try {
                    return OptimusElementFinder.optimusElementFinder(null).findBy(user, pageName, elementName, param);
                } catch (OptimusException | IOException e) {
                    throw new RuntimeException(String.format("Can't locate element '%s' on Page '%s' with argument '%s'", elementName, pageName, param), e);
                }
            }
            clazz = clazz.getSuperclass();
        }

        throw new RuntimeException(String.format("Cannot determine how to locate instance of %s", elementClass));
    }

    /**
     * Defines whether or not given element
     * should be returned from cache on further calls.
     *
     * @return boolean if lookup cached
     */
    @Override
    public boolean isLookupCached() {
        return false;
    }
}
