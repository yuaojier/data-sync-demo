package aop;

import cn.hutool.core.util.ClassUtil;
import org.springframework.aop.support.StaticMethodMatcher;

import java.lang.reflect.Method;

public class JavaTypeMatcher extends StaticMethodMatcher {
    private Class<?> matcherClass;

    public JavaTypeMatcher(Class<?> matcherClass) {
        this.matcherClass = matcherClass;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return ClassUtil.isAssignable(matcherClass, targetClass);
    }
}
