package event;

/**
 * "事件处理通知" 处理器
 *
 */
public interface EventHandler<T extends Event> {
    /**
     * 是否支持
     *
     * @param event
     * @return
     */
    boolean supports(T event);

    /**
     * 具体处理
     *
     * @param event
     * @throws Throwable 处理失败时抛出异常
     */
    void handler(T event) throws Throwable;
}
