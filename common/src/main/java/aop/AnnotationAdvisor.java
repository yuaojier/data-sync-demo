package aop;

import org.springframework.aop.support.annotation.AnnotationMethodMatcher;

import java.lang.annotation.Annotation;

public class AnnotationAdvisor extends MethodMatcherAdvisor {
    private static final long serialVersionUID = 4234912194450884461L;


    public AnnotationAdvisor(Class<? extends Annotation>[] annotations) {
        AnnotationMethodMatcher[] annotationMethodMatchers = new AnnotationMethodMatcher[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            annotationMethodMatchers[i] = new AnnotationMethodMatcher(annotations[i]);
        }
        setMethodMatchers(annotationMethodMatchers);
    }

    public AnnotationAdvisor(Class<? extends Annotation> annotationClass) {
        this(new Class[]{annotationClass});
    }
}