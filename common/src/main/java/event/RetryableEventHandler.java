package event;

import cn.hutool.core.exceptions.ExceptionUtil;
import retry.BreakRetryException;
import retry.Retryable;

public interface RetryableEventHandler<T extends Event> extends EventHandler<T>, Retryable<T, Void> {

    @Override
    default Void retry(T input, int retryTimes) {
        try {
            handler(input);
        } catch (Throwable throwable) {
            throw ExceptionUtil.wrapRuntime(throwable);
        }
        throw new BreakRetryException();
    }
}
