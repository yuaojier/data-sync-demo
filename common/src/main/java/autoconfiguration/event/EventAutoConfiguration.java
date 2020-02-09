package autoconfiguration.event;

import event.EventListener;
import event.SpringNativeEventChannel;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfigureBefore
@Configuration
public class EventAutoConfiguration {
    @Bean
    public EventListener eventListener() {
        EventListener eventListener = new EventListener();
        return eventListener;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringNativeEventChannel springNativeEventChannel(EventListener eventListener) {
        SpringNativeEventChannel eventChannel = new SpringNativeEventChannel();
        eventChannel.setEventListener(eventListener);
        return eventChannel;
    }
}
