package delay;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import utils.LoggerUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AbstractDelayChannel implements DelayChannel, InitializingBean {
    private static final String TRACEABLE_EXECUTOR_CLASS = "org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService";

    @Autowired
    private BeanFactory beanFactory;

    private ListeningExecutorService executorService;

    private DelayTaskExecutor delayTaskExecutor;

    private DelayTaskStorage taskStorage;

    @Override
    public void add(long delay, DelayTask delayTask) {
        if (taskStorage.add(delayTask.getName(), delayTask.getTaskKey())) {
            scheduledTask(delay, delayTask);
        }
    }

    protected abstract void scheduledTask(long delay, DelayTask delayTask);

    @Override
    public void cancel(String bizName, String taskKey) {
        taskStorage.remove(bizName, taskKey);
    }

    /**
     * 执行延迟任务
     *
     * @param delayTask
     */
    protected void executeDelayTask(DelayTask delayTask) {

        ListenableFuture<?> future = executorService.submit(() -> {
            String taskName = delayTask.getName();
            String taskKey = delayTask.getTaskKey();

            if (taskStorage.isRemoved(taskName, taskKey)) {
                if (log.isDebugEnabled()) {
                    log.debug("任务已取消, taskName:{}, taskKey:{}", taskName, taskKey);
                }
            } else {
                if (taskStorage.isRecover(taskName, taskKey)) {
                    delayTask.setPollingTimes(0);
                }
                delayTaskExecutor.executeDelayTask(delayTask);
            }
            taskStorage.clear(taskName, taskKey);
        });
        Futures.addCallback(future, new FutureCallback<Object>() {

            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(Throwable t) {
                LoggerUtils.error(log, "延迟任务异常", t, delayTask);
            }
        });

    }

    @Autowired
    public void setDelayTaskExecutor(DelayTaskExecutor delayTaskExecutor) {
        this.delayTaskExecutor = delayTaskExecutor;
    }

    public void setTaskStorage(DelayTaskStorage taskStorage) {
        this.taskStorage = taskStorage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(64);
        if (ClassUtils.isPresent(TRACEABLE_EXECUTOR_CLASS, AbstractDelayChannel.class.getClassLoader())) {
            executorService = ReflectUtil.newInstance(ClassUtil.loadClass(TRACEABLE_EXECUTOR_CLASS), beanFactory, executorService);
        }
        this.executorService = MoreExecutors.listeningDecorator(executorService);
    }
}
