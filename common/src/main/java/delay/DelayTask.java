package delay;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务延迟任务
 *
 */
@Data
@NoArgsConstructor
public class DelayTask {
    /**
     * 轮询次数
     */
    private int pollingTimes;

    /**
     * 业务名称
     */
    private String name;

    /**
     * 任务唯一标识
     */
    private String taskKey;

    /**
     * 业务参数, 目前只数组长度固定为1
     */
    private Object[] objects;

    @Builder
    public DelayTask(String name, String taskKey, Object[] objects) {
        this.name = name;
        this.taskKey = taskKey;
        this.objects = objects;
    }
}
