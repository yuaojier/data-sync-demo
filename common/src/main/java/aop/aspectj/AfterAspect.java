package aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;

/**
 * @param <T> 方法返回类型
 */
public interface AfterAspect<T> extends SimpleAspect {
    /**
     * @param joinPoint
     * @param originReturn 原方法返回
     */
    @AfterReturning(value = POINTCUT, returning = "originReturn")
    void afterAspect(JoinPoint joinPoint, T originReturn);
}
