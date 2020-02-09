package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractMultiParallelThreadHandler implements MultiThreadHandler {

    /**
     * 子线程倒计数锁
     */
    protected CountDownLatch childLatch;

    /**
     * 任务列表
     */
    protected List<Runnable> taskList;

    /**
     * 子线程异常
     */
    protected ChildThreadException childThreadException;

    public AbstractMultiParallelThreadHandler() {
        taskList = new ArrayList<Runnable>();
        childThreadException = new ChildThreadException();
    }

    public void setCountDownLatch(CountDownLatch latch) {
        this.childLatch = latch;
    }

    @Override
    public void addTask(Runnable... tasks) {
        if (null == tasks) {
            taskList = new ArrayList<Runnable>();
        }
        for (Runnable task : tasks) {
            taskList.add(task);
        }
    }

    @Override
    public abstract void run() throws Exception;

}
