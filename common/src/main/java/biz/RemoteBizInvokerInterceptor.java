package biz;

import aop.aspectj.AfterThrowingAspect;
import biz.exception.RemoteBizUnknownException;
import delay.DelayChannel;
import delay.DelayTask;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 */
@Aspect
public class RemoteBizInvokerInterceptor implements AfterThrowingAspect {

    private DelayChannel delayChannel;

    public void setDelayChannel(DelayChannel delayChannel) {
        this.delayChannel = delayChannel;
    }

    @Override
    @Pointcut("execution(public * biz.RemoteBizInvoker.invoke(..))")
    public void pointcut() {

    }

    @Override
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable ex) {
        RemoteBizInvoker bizInvoker = (RemoteBizInvoker) joinPoint.getThis();
        if (ex instanceof RemoteBizUnknownException) {
            //触发 轮询逻辑
            delayChannel.add(bizInvoker.getRetryPolicy().getRetryPeriod(), new DelayTask(bizInvoker.getName(), bizInvoker.getBizKey(joinPoint.getArgs()[0]), joinPoint.getArgs()));
        }
    }
}
