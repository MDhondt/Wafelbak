package be.scoutsronse.wafelbak.tech.event.eventbus;

import java.lang.reflect.Method;

public interface EventBus {

    void register(Object eventHandler, Method method);

    void unregister(Object eventHandler);

    void post(Object event);
}