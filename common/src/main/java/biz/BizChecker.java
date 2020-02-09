package biz;

import cn.hutool.core.exceptions.ExceptionUtil;
import delay.DelayChannel;
import delay.DelayTask;
import retry.Retryable;

/**
 * 检查业务状态
 * <p>
 * {@link DelayChannel#add(long, DelayTask)}创建延迟任务
 * <p>
 * <pre>
 * <code>
 *
 * class FooChecker implements BizChecker<FooIn, FooOut> {
 *
 *    public String getName(){
 *        return "fooChecker";
 *    }
 *
 *    public FooOut check(FooIn fooIn) {
 *        //业务逻辑
 *    }
 *
 * }
 * </code>
 * </pre>
 *
 * 添加延迟任务逻辑
 * <pre>
 * <code>
 *     //任务入参
 *     FooIn fooIn = ...;
 *     DelayTask delayTask = DelayTask.builder()
 *                      .name("fooChecker")
 *                      .taskKey("业务标识")
 *                      .objects(new Object[]{fooIn})
 *                      .build();
 *     delayChannel.add(1000 * 60 * 60 * 24, delayTask);
 *
 * </code>
 * </pre>
 *
 */
public interface BizChecker<I, O> extends Retryable<I, O> {
    RetryPolicy CHECK_POLICY = RetryPolicy
            .builder()
            .maxAttempts(0)
            .retryPeriod(0)
            .delay(0)
            .build();

    @Override
    default O retry(I input, int retryTimes) {
        try {
            return checkBiz(input);
        } catch (Exception e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    O checkBiz(I input);

    @Override
    default RetryPolicy getRetryPolicy() {
        return CHECK_POLICY;
    }
}
