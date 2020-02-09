package delay;

/**
 * 延迟通道, 延迟实现方式; 延迟业务逻辑需实现{@link retry.Retryable};
 * <p>
 * 子类
 * <p>
 * {@link biz.RemoteBizInvoker} 远程业务接口
 * <p>
 * {@link biz.BizChecker} 业务检查接口
 *
 */
public interface DelayChannel {

    /**
     * 增加延迟任务
     *
     * @param delay     延迟时间, 单位毫秒
     * @param delayTask
     */
    void add(long delay, DelayTask delayTask);

    /**
     * 移除任务
     *
     * @param bizName
     * @param taskKey
     */
    void cancel(String bizName, String taskKey);
}
