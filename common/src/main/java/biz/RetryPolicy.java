package biz;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 重试策略
 */
@Data
@NoArgsConstructor
public class RetryPolicy {
    /**
     * 延迟时间
     */
    private long delay;

    /**
     * 重试次数
     */
    private int maxAttempts;

    /**
     * 重试间隔
     */
    private long retryPeriod;

    @Builder
    public RetryPolicy(long delay, int maxAttempts, long retryPeriod) {
        this.delay = delay;
        this.maxAttempts = maxAttempts;
        this.retryPeriod = retryPeriod;
    }
}
