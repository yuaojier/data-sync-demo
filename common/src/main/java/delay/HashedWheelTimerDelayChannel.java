package delay;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Netty时间轮实现方式, 恢复<B>单机测试时使用, 生产环境慎用, 内存/CPU会随任务量直线飙升</B>
 */
@Slf4j
public class HashedWheelTimerDelayChannel extends AbstractDelayChannel {
    private Timer timer = new HashedWheelTimer();

    protected void scheduledTask(long delay, DelayTask delayTask) {
        timer.newTimeout(timeout -> executeDelayTask(delayTask), delay, TimeUnit.MILLISECONDS);
    }

}
