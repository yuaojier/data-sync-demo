package retry;


import biz.RetryPolicy;

/**
 * @param <I> 入参
 * @param <O> 出参
 */
public interface Retryable<I, O> {
    /**
     * 重试策略
     *
     * @return
     */
    RetryPolicy getRetryPolicy();

    /**
     * 重试服务名称, 需保证唯一
     *
     * @return
     */
    String getName();

    O retry(I input, int retryTimes);
}
