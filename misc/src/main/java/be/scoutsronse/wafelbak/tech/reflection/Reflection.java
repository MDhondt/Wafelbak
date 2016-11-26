package be.scoutsronse.wafelbak.tech.reflection;

import com.google.common.collect.Lists;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class Reflection {

    public static Type[] getActualTypeArguments(Class<?> clazz) {
        return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
    }

    public static Collection<Method> findAllMethodsWithAnnotation(final Object obj, final Class<? extends Annotation> annotationClass) {
        return getMethods(obj).stream().filter(method -> method.isAnnotationPresent(annotationClass)).collect(toList());
    }

    private static Collection<Method> getMethods(Object obj) {
        Class<?> clazz = getClass(obj);
        return concat(concat(singletonList(clazz).stream(), superClassesOf(clazz).stream()), allInterfacesOf(clazz).stream()).flatMap(c -> stream(c.getDeclaredMethods())).collect(toList());
    }

    private static Class<? extends Object> getClass(Object obj) {
        return target(obj).getClass();
    }

    private static Collection<? extends Class<?>> superClassesOf(Class<?> clazz) {
        List<Class<?>> classes = Lists.newArrayList();
        while (clazz.getSuperclass() != null) {
            classes.add(clazz.getSuperclass());
            clazz = clazz.getSuperclass();
        }
        return classes;
    }

    private static Collection<? extends Class<?>> allInterfacesOf(Class<?> clazz) {
        return concat(superClassesOf(clazz).stream().flatMap(superClazz -> Lists.newArrayList(superClazz.getInterfaces()).stream()), Stream.of(clazz.getInterfaces())).collect(toList());
    }

    private static <T> T target(T object) {
        if (AopUtils.isAopProxy(object)) {
            try {
                return (T) ((Advised) object).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return (T) object;
        }
    }
}