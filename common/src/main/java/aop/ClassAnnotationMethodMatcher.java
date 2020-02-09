package aop;

import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ClassAnnotationMethodMatcher extends StaticMethodMatcher {
    private Class<? extends Annotation> annotationClass;

    public ClassAnnotationMethodMatcher(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return AnnotatedElementUtils.isAnnotated(targetClass, annotationClass);
    }
}
