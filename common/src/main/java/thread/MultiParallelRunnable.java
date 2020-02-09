package thread;

public class MultiParallelRunnable implements Runnable {
    /**
     * 并行任务参数
     */
    private MultiParallelContext context;

    /**
     * 构造函数
     *
     * @param context
     */
    public MultiParallelRunnable(MultiParallelContext context) {
        this.context = context;
    }

    /**
     * 运行任务
     */
    @Override
    public void run() {
        try {
            Runnable task = context.getTask();
            if (task instanceof FunctionRunnable) {
                ((FunctionRunnable) task).setChildThreadException(context.getChildException());
            } else if (task instanceof MenuRunnableApi) {
                ((MenuRunnableApi) task).setChildThreadException(context.getChildException());
            }
            task.run();
        } catch (Exception e) {
            context.getChildException().addException(e);
        } finally {
            context.getChildLatch().countDown();
        }
    }
}
