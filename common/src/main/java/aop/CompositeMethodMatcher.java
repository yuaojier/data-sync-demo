package aop;

import org.springframework.aop.MethodMatcher;
import org.springframework.aop.support.StaticMethodMatcher;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public class CompositeMethodMatcher extends StaticMethodMatcher {
    private MethodMatcher[] methodMatchers;

    /**
     * 匹配一个
     */
    private boolean matchOne;

    public CompositeMethodMatcher(MethodMatcher[] methodMatchers, boolean matchOne) {
        this.methodMatchers = methodMatchers;
        this.matchOne = matchOne;
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (matchOne) {
            return Stream.of(methodMatchers).anyMatch(methodMatcher -> methodMatcher.matches(method, targetClass));
        } else {
            return Stream.of(methodMatchers).allMatch(methodMatcher -> methodMatcher.matches(method, targetClass));
        }
    }
}
