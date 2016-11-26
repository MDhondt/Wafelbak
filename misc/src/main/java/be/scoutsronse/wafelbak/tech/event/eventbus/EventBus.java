package be.scoutsronse.wafelbak.tech.event.eventbus;

public interface EventBus {

    void register(Object eventHandler);

    void unregister(Object eventHandler);

    void post(Object event);
}