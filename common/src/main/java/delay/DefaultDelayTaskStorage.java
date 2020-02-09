package delay;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultDelayTaskStorage implements DelayTaskStorage {
    private TaskStorage paddingTask;

    private TaskStorage recoverTask;

    private TaskStorage removeTask;

    @Override
    public boolean add(String taskName, String taskKey) {
        if (paddingTask.contains(taskKey, taskKey)) {
            if (log.isDebugEnabled()) {
                log.debug("延迟任务已存在, name:{}, key:{}", taskName, taskKey);
            }
            recoverTask.add(taskName, taskKey);
            return false;
        } else {
            removeTask.remove(taskName, taskName);
            paddingTask.add(taskName, taskKey);
            return true;
        }
    }

    @Override
    public void remove(String taskName, String taskKey) {
        removeTask.add(taskName, taskKey);
    }

    @Override
    public boolean contains(String taskName, String taskKey) {
        return paddingTask.contains(taskName, taskKey);
    }

    @Override
    public boolean isRemoved(String taskName, String taskKey) {
        return removeTask.remove(taskName, taskKey);
    }

    @Override
    public boolean isRecover(String taskName, String taskKey) {
        return recoverTask.remove(taskName, taskKey);
    }

    @Override
    public void clear(String taskName, String taskKey) {
        paddingTask.remove(taskName, taskKey);
        recoverTask.remove(taskName, taskKey);
//        removeTask.remove(taskName, taskKey);
    }

    public void setPaddingTask(TaskStorage paddingTask) {
        this.paddingTask = paddingTask;
    }

    public void setRecoverTask(TaskStorage recoverTask) {
        this.recoverTask = recoverTask;
    }

    public void setRemoveTask(TaskStorage removeTask) {
        this.removeTask = removeTask;
    }
}