package core;

import annotations.StoreElement;
import com.testvagrant.commons.exceptions.OptimusException;
import finder.OptimusElementFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.io.IOException;
import java.lang.reflect.Field;

public class StoreElementAnnotations extends Annotations {

    private Field field;
    private String pageName;
    private String user;

    /**
     * @param field expected to be an element in a Page Object
     */
    public StoreElementAnnotations(Field field, String pageName, String user) {
        super(field);
        this.field = field;
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
        StoreElement storeElement = this.field.getAnnotation(StoreElement.class);
        if (storeElement == null) {
            return super.buildByFromDefault();
        }
        String elementName = storeElement.value();
        String param = storeElement.param();

        try {
            return OptimusElementFinder.optimusElementFinder(null).findBy(user, pageName, elementName, param);
        } catch (OptimusException | IOException e) {
            throw new RuntimeException(String.format("Can't locate element '%s' on Page '%s' with argument '%s'", elementName, pageName, param), e);
        }
    }
}
