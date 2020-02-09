package thread;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MultiParallelThreadHandler extends AbstractMultiParallelThreadHandler {

    public MultiParallelThreadHandler() {
        super();
    }

    @Override
    public void run() throws Exception {
        if (null == taskList || taskList.size() == 0) {
            return;
        } else if (taskList.size() == 1) {
            runWithoutNewThread();
        } else if (taskList.size() > 1) {
            runInNewThread();
        }
    }

    /**
     * 新建线程运行任务
     *
     * @throws ChildThreadException
     */
    private void runInNewThread() throws Exception {
        childLatch = new CountDownLatch(taskList.size());
        childThreadException.clearExceptionList();
        for (Runnable task : taskList) {
            invoke(new MultiParallelRunnable(new MultiParallelContext(task, childLatch, childThreadException)));
        }
        taskList.clear();
        try {
            childLatch.await();
        } catch (InterruptedException e) {
            childThreadException.addException(e);
        }
        throwChildExceptionIfRequired();
    }

    /**
     * 默认线程执行方法
     *
     * @param command
     */
    protected void invoke(Runnable command) {
        if(command.getClass().isAssignableFrom(Thread.class)){
            Thread.class.cast(command).start();
        }else{
            new Thread(command).start();
        }
    }

    /**
     * 在当前线程中直接运行
     *
     * @throws ChildThreadException
     */
    private void runWithoutNewThread() throws Exception {
        try {
            taskList.get(0).run();
        } catch (Exception e) {
            childThreadException.addException(e);
        }
        throwChildExceptionIfRequired();
    }

    /**
     * 根据需要抛出子线程异常
     *
     * @throws ChildThreadException
     */
    private void throwChildExceptionIfRequired() throws Exception {
        if (childThreadException.hasException()) {
            childExceptionHandler(childThreadException);
        }
    }

    /**
     * 默认抛出子线程异常
     * @param e
     * @throws ChildThreadException
     */
    protected void childExceptionHandler(ChildThreadException e) throws Exception {
        List<Exception> exceptionList = e.getExceptionList();
        if (CollectionUtil.isNotEmpty(exceptionList)) {
            throw exceptionList.get(0);
        }
    }
}
