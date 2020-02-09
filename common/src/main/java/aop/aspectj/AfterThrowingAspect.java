package aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;

public interface AfterThrowingAspect extends SimpleAspect {
    @AfterThrowing(value = POINTCUT, throwing = "ex")
    void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex);
}
