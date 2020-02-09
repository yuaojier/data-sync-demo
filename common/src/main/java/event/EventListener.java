package event;

import biz.RetryPolicy;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import delay.DelayChannel;
import delay.DelayTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import retry.Retryable;
import utils.LoggerUtils;

/**
 * 处理通知监听
 *
 */
@Slf4j
public class EventListener implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private DelayChannel delayChannel;

    public void onEvent(Event event) {
        String[] handlerBeanNames = applicationContext.getBeanNamesForType(EventHandler.class);
        boolean hasHandler = false;
        if (ArrayUtil.isNotEmpty(handlerBeanNames)) {
            for (String handlerBeanName : handlerBeanNames) {
                EventHandler handler = (EventHandler) applicationContext.getBean(handlerBeanName);
                Class eventType = ResolvableType.forInstance(handler).as(EventHandler.class).getGeneric(0).resolve();
                boolean support = false;
                try {
                    support = ClassUtil.isAssignable(eventType, event.getClass()) && handler.supports(event);
                } catch (Exception e) {
                    LoggerUtils.error(log, "Eventhandler#supports", e);
                }
                if (support) {
                    hasHandler = true;
                    try {
                        handler.handler(event);
                        if (log.isDebugEnabled()) {
                            log.debug("{} handle event, {}", handler.getClass(), ReflectionToStringBuilder.toString(event));
                        }
                    } catch (Throwable throwable) {
                        if (handler instanceof Retryable) {
                            Retryable retryable = ((Retryable) handler);
                            RetryPolicy retryPolicy = retryable.getRetryPolicy();
                            if (retryPolicy != null) {
                                //暂时使用event.toString() 作为任务Id
                                delayChannel.add(retryPolicy.getDelay(), new DelayTask(retryable.getName(), event.toString(), new Object[]{event}));
                            } else {
                                LoggerUtils.error(log, "处理事件异常, 重试策略为null", throwable, handler.getClass(), ReflectionToStringBuilder.toString(event));
                            }
                        } else {
                            LoggerUtils.error(log, "处理事件异常", throwable, handler.getClass(), ReflectionToStringBuilder.toString(event));
                        }
                    }
                }
            }
        }

        if (!hasHandler && log.isWarnEnabled()) {
            log.warn("event has not handler, {}", ToStringBuilder.reflectionToString(event));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        delayChannel = applicationContext.getBean(DelayChannel.class);
        this.applicationContext = applicationContext;
    }
}
