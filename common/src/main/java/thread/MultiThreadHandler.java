package thread;

/**
 * 多任务处理
 */
public interface MultiThreadHandler {

    /**
     * 添加任务
     *
     * @param tasks
     */
    void addTask(Runnable... tasks);

    /**
     * 执行任务
     */
    void run() throws Exception;
}
