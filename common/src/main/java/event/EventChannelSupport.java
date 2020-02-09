package event;

import lombok.NonNull;

public abstract class EventChannelSupport implements EventChannel {
    protected EventListener eventListener;

    public void setEventListener(@NonNull EventListener eventListener) {
        this.eventListener = eventListener;
        registerListener(eventListener);
    }
}
