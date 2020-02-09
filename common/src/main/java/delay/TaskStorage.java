package delay;

public interface TaskStorage {
    /**
     * @param taskName
     * @param taskKey
     * @return 任务已存在时返回false
     */
    boolean add(String taskName, String taskKey);

    boolean remove(String taskName, String taskKey);

    boolean contains(String taskName, String taskKey);

}
