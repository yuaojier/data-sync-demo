package event;

public interface EventChannel {
    /**
     * 发布事件
     *
     * @param event
     */
    void publishEvent(Event event);

    void registerListener(EventListener eventListener);
}
