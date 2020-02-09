package aop;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

@Slf4j
public class MethodMatcherAdvisor extends StaticMethodMatcherPointcutAdvisor {
    private static final long serialVersionUID = 4234912194450884461L;

    private MethodMatcher[] methodMatchers;

    public MethodMatcherAdvisor(MethodMatcher[] methodMatchers) {
        this.methodMatchers = methodMatchers;
    }

    public MethodMatcherAdvisor(MethodMatcher methodMatchers) {
        this(new MethodMatcher[]{methodMatchers});
    }

    protected MethodMatcherAdvisor() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (ArrayUtil.isEmpty(methodMatchers)) {
            if (log.isWarnEnabled()) {
                log.warn("methodMatchers is empty, ignore interceptor {}", getAdvice());
            }
        } else {
            for (MethodMatcher methodMatcher : methodMatchers) {
                if (methodMatcher.matches(method, targetClass))
                    return true;
            }
        }
        return false;
    }

    public void setMethodMatchers(MethodMatcher[] methodMatchers) {
        this.methodMatchers = methodMatchers;
    }
}