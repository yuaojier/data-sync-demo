package aop;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Slf4j
public abstract class AbstractAnnotationInterceptor<T extends Annotation> extends AbstractInterceptor {
    private Class<T> annotationClass;

    protected T getAnnotation(Method method) {
        return method.getAnnotation(annotationClass);
    }

    public AbstractAnnotationInterceptor(Class<T> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        T annotation = invocation.getMethod().getAnnotation(annotationClass);

        if (annotation != null) {
            doBefore(invocation, annotation);
            Object methodReturn = invocation.proceed();
            try {
                doAfter(invocation, annotation, methodReturn);
            } catch (Exception e) {
                log.error("Exception with doAfter, ignore and return", e);
            }
            return methodReturn;
        } else {
            return invocation.proceed();
        }
    }

    protected abstract void doBefore(MethodInvocation invocation, T annotation);

    protected abstract void doAfter(MethodInvocation invocation, T annotation, Object methodReturn);
}