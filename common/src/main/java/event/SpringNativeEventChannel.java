package event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

public class SpringNativeEventChannel extends EventChannelSupport
        implements ApplicationContextAware, ApplicationListener<PayloadApplicationEvent> {
    private ApplicationContext applicationContext;

    @Override
    public void publishEvent(Event event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void registerListener(EventListener eventListener) {
        //自动注册无需事先
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(PayloadApplicationEvent event) {
        if (event.getPayload() instanceof Event) {
            eventListener.onEvent((Event) event.getPayload());
        }
    }
}
