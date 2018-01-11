package be.scoutsronse.wafelbak.tech.event.eventbus;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@Primary
public class PrioritizedEventBus implements EventBus {

    private LinkedHashMap<Integer, SynchronousEventBus> eventBuses;

    public void initialisePriorities(List<Integer> existingPriorities) {
        eventBuses = new LinkedHashMap<>();
        existingPriorities.stream().sorted().forEach(priority -> eventBuses.put(priority, new SynchronousEventBus()));
    }

    @Override
    public void register(Object eventHandler, Method method) {
        int priority = method.getAnnotation(Subscribe.class).priority();
        eventBuses.get(priority).register(eventHandler, method);
    }

    @Override
    public void unregister(Object eventHandler) {
        eventBuses.values().forEach(eventBus -> eventBus.unregister(eventHandler));
    }

    @Override
    public void post(Object event) {
        eventBuses.values().forEach(eventBus -> eventBus.post(event));
    }
}