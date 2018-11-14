package utils;

import annotations.StoreElement;
import com.google.common.collect.Lists;
import elements.BlockElement;
import elements.TypifiedElement;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.*;
import java.util.List;

public class PageFactoryUtils {

    public static <T> T newInstance(Class<T> clazz, Object... args) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            Class outerClass = clazz.getDeclaringClass();
            Object outerObject = outerClass.newInstance();
            return ConstructorUtils.invokeConstructor(clazz, Lists.asList(outerObject, args).toArray());
        }
        return ConstructorUtils.invokeConstructor(clazz, args);
    }

    public static boolean isBlockElement(Field field) {
        return isBlockElement(field.getType());
    }

    public static boolean isBlockElement(Class<?> clazz) {
        return BlockElement.class.isAssignableFrom(clazz);
    }

    public static <T> boolean isBlockElement(T instance) {
        return instance instanceof BlockElement;
    }

    public static boolean isTypifiedElement(Field field) {
        return isTypifiedElement(field.getType());
    }

    public static boolean isTypifiedElement(Class<?> clazz) {
        return TypifiedElement.class.isAssignableFrom(clazz);
    }

    public static boolean isWebElement(Field field) {
        return isWebElement(field.getType());
    }

    public static boolean isWebElement(Class<?> clazz) {
        return WebElement.class.isAssignableFrom(clazz);
    }

    public static boolean isHtmlElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return isBlockElement(listParameterClass);
    }

    public static boolean isTypifiedElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return isTypifiedElement(listParameterClass);
    }

    public static boolean isWebElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }
        Class listParameterClass = getGenericParameterClass(field);
        return isWebElement(listParameterClass);
    }

    public static Class getGenericParameterClass(Field field) {
        Type genericType = field.getGenericType();
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private static boolean isParametrizedList(Field field) {
        return isList(field) && hasGenericParameter(field);
    }

    private static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static boolean hasGenericParameter(Field field) {
        return field.getGenericType() instanceof ParameterizedType;
    }

    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(StoreElement.class)) {
            return field.getAnnotation(StoreElement.class).value();
        }
        if (field.getType().isAnnotationPresent(StoreElement.class)) {
            return field.getType().getAnnotation(StoreElement.class).value();
        }
        return splitCamelCase(field.getName());
    }

    public static <T> String getElementName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(StoreElement.class)) {
            return clazz.getAnnotation(StoreElement.class).value();
        }
        return splitCamelCase(clazz.getSimpleName());
    }

    private static String splitCamelCase(String camel) {
        return WordUtils.capitalizeFully(camel.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        ));
    }

}
