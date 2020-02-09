package delay;

import biz.*;
import biz.exception.RemoteBizFailureException;
import biz.exception.RemoteBizUnknownException;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import event.EventChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import retry.BreakRetryException;
import retry.Retryable;
import utils.BeanUtils;
import utils.LoggerUtils;

/**
 */
@Slf4j
public class DelayTaskExecutor implements ApplicationContextAware, InitializingBean {
    private DelayChannel delayChannel;

    private EventChannel eventChannel;

    private ApplicationContext applicationContext;

    public void executeDelayTask(DelayTask delayTask) {
        String[] beanNames = applicationContext.getBeanNamesForType(Retryable.class);
        if (beanNames != null) {
            for (String beanName : beanNames) {
                Retryable retryable = applicationContext.getBean(beanName, Retryable.class);
                if (StrUtil.equals(retryable.getName(), delayTask.getName())) {
                    execute(retryable, delayTask);
                    break;
                }
            }
        }
    }

    protected void execute(Retryable retryable, DelayTask delayTask) {
        RetryPolicy retryPolicy = retryable.getRetryPolicy();
        Object response = null;
        boolean unknown = false;
        try {
            response = retryable.retry(delayTask.getObjects()[0], delayTask.getPollingTimes());
        } catch (Exception e) {
            if (ExceptionUtil.isCausedBy(e, RemoteBizFailureException.class)) {
                if (log.isDebugEnabled()) {
                    log.warn(StrUtil.format("{}第{}次重试, 返回失败, param:{}", retryable.getName(), delayTask.getPollingTimes(), ToStringBuilder.reflectionToString(delayTask.getObjects()[0])), e);
                }
            } else if (ExceptionUtil.isCausedBy(e, RemoteBizUnknownException.class)) {
                if (log.isDebugEnabled()) {
                    log.warn(StrUtil.format("{}第{}次重试, 返回未知, param:{}", retryable.getName(), delayTask.getPollingTimes(), ToStringBuilder.reflectionToString(delayTask.getObjects()[0])), e);
                }
                unknown = true;
            } else if (ExceptionUtil.isCausedBy(e, BreakRetryException.class)) {
                //退出重试
                if (log.isDebugEnabled()) {
                    log.warn(StrUtil.format("{}第{}次重试, 强制退出, param:{}", retryable.getName(), delayTask.getPollingTimes(), ToStringBuilder.reflectionToString(delayTask.getObjects()[0])), e);
                }
            } else {
                log.warn(StrUtil.format("{}第{}次重试, 处理异常, param:{}", retryable.getName(), delayTask.getPollingTimes(), ToStringBuilder.reflectionToString(delayTask.getObjects()[0])), e);
                unknown = true;
            }
        }

        if (retryable instanceof RemoteBizInvoker) {
            RemoteBizInvoker bizInvoker = (RemoteBizInvoker) retryable;
            if (response != null) {
                RemoteBizEvent bizEvent = newBizEvent(((RemoteBizInvoker) retryable).getEventType(), retryable.getName(), delayTask.getObjects()[0], RemoteBizState.SUCCESS);
                bizEvent.setResponse(response);
                eventChannel.publishEvent(bizEvent);
            } else {
                if (retryPolicy.getMaxAttempts() > delayTask.getPollingTimes() && unknown) {
                    delayChannel.add(retryPolicy.getRetryPeriod(), newTask(retryable.getName(), delayTask.getTaskKey(), delayTask.getPollingTimes() + 1, delayTask.getObjects()));

                } else if (unknown && bizInvoker instanceof RemoteBizCancelInvoker) {
                    RemoteBizEvent bizEvent = newBizEvent(bizInvoker.getEventType(), bizInvoker.getName(), delayTask.getObjects()[0], RemoteBizState.CANCEL);
                    try {
                        ((RemoteBizCancelInvoker) bizInvoker).cancel(delayTask.getObjects()[0]);
                        bizEvent.setState(RemoteBizState.CANCEL);
                    } catch (RemoteBizFailureException e) {
                        LoggerUtils.error(StrUtil.format("{}取消, 返回失败", bizInvoker.getName(), delayTask.getPollingTimes()), e, delayTask.getObjects()[0]);
                    } catch (RemoteBizUnknownException e) {
                        LoggerUtils.error(StrUtil.format("{}取消, 返回未知", bizInvoker.getName(), delayTask.getPollingTimes()), e, delayTask.getObjects()[0]);
                    }
                    eventChannel.publishEvent(bizEvent);

                } else {
                    eventChannel.publishEvent(newBizEvent(bizInvoker.getEventType(), bizInvoker.getName(), delayTask.getObjects()[0], RemoteBizState.FAIL));
                }
            }
        } else {
            if (retryPolicy.getMaxAttempts() > delayTask.getPollingTimes() && unknown) {
                delayChannel.add(retryPolicy.getRetryPeriod(), newTask(retryable.getName(), delayTask.getTaskKey(), delayTask.getPollingTimes() + 1, delayTask.getObjects()));
            }
        }
    }

    private void tryCancelBiz(RemoteBizInvoker bizInvoker, DelayTask delayTask) {

    }


    private RemoteBizEvent newBizEvent(Class<? extends RemoteBizEvent> clazz, String bizName, Object request, RemoteBizState state) {
        RemoteBizEvent bizEvent = BeanUtils.newInstance(clazz);
        bizEvent.setSourceTag(bizName);
        bizEvent.setRequest(request);
        bizEvent.setState(state);
        return bizEvent;
    }

    private DelayTask newTask(String bizName, String taskKey, int pollingTimes, Object[] objects) {
        DelayTask delayTask = new DelayTask(bizName, taskKey, objects);
        delayTask.setPollingTimes(pollingTimes);
        return delayTask;
    }

    @Autowired
    public void setEventChannel(EventChannel eventChannel) {
        this.eventChannel = eventChannel;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        delayChannel = applicationContext.getBean(DelayChannel.class);
    }
}
