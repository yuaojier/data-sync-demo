package autoconfiguration.delay;

import delay.DelayChannel;
import delay.DelayTaskStorage;
import delay.HashedWheelTimerDelayChannel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

@ConditionalOnMissingBean(DelayChannel.class)
@Conditional(DelayCondition.class)
public class HashedWheelDelayConfiguration {
    @Bean
    public HashedWheelTimerDelayChannel nettyDelayChannel(DelayTaskStorage taskStorage) {
        HashedWheelTimerDelayChannel delayChannel = new HashedWheelTimerDelayChannel();
        delayChannel.setTaskStorage(taskStorage);
        return delayChannel;
    }
}
