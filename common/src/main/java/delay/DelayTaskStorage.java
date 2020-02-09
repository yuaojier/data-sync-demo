package delay;

public interface DelayTaskStorage {
    /**
     * @param taskName
     * @param taskKey
     * @return 任务已存在时返回false
     */
    boolean add(String taskName, String taskKey);

    void remove(String taskName, String taskKey);

    boolean contains(String taskName, String taskKey);

    /**
     * 是否是已删除任务
     *
     * @param taskName
     * @param taskKey
     * @return
     */
    boolean isRemoved(String taskName, String taskKey);

    /**
     * 是否是恢复任务; 运行中任务, 重复添加后将任务状态恢复为第1次执行
     *
     * @param taskName
     * @param taskKey
     * @return
     */
    boolean isRecover(String taskName, String taskKey);

    void clear(String taskName, String taskKey);
}
