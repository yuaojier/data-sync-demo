package biz;

import event.Event;
import lombok.Data;
import lombok.NonNull;

/**
 * 远程业务处理事件
 */
@Data
public abstract class RemoteBizEvent<R, S> extends Event {
    private static final long serialVersionUID = -1407197757771389906L;

    protected RemoteBizState state;

    /**
     * 业务结果
     */
    protected S response;

    protected R request;

    public RemoteBizEvent(@NonNull String bizName, @NonNull R request, @NonNull RemoteBizState state) {
        super(bizName);
        this.request = request;
        this.state = state;
    }
}
