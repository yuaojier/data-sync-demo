package aop.aspectj;

import org.aspectj.lang.annotation.Before;

public interface BeforeAspect extends SimpleAspect {

    @Before(POINTCUT)
    public void beforeAdvice();
}
