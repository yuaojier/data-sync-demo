package aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
public abstract class AroundAspect<T> implements SimpleAspect {
    @Pointcut(POINTCUT)
    Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        AroundContext<T> aroundContext = new AroundContext<>();
        before(joinPoint, aroundContext);

        T originReturn;
        if (aroundContext.getBeforeReturn() == null) {
            try {
                originReturn = (T) joinPoint.proceed();
            } catch (Throwable throwable) {
                afterThrowing(joinPoint, throwable);
                throw throwable;
            }
            aroundContext.setOriginReturn(originReturn);
        } else {
            originReturn = aroundContext.getOriginReturn();
        }

        try {
            after(joinPoint, aroundContext);
        } catch (Exception e) {
            // FIXME: 2020/2/9   do something
        }
        return originReturn;

    }

    protected abstract void before(ProceedingJoinPoint joinPoint, AroundContext<T> aroundContext);

    protected abstract void after(ProceedingJoinPoint joinPoint, AroundContext<T> aroundContext);

    protected abstract void afterThrowing(ProceedingJoinPoint joinPoint, Throwable throwable);
}
