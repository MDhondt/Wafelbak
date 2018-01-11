package be.scoutsronse.wafelbak.tech.event.eventbus;

import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ArrayListMultimap.create;
import static com.google.common.collect.Multimaps.filterEntries;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Proxy.getInvocationHandler;
import static org.springframework.aop.support.AopUtils.isJdkDynamicProxy;

public abstract class EventBusImpl implements EventBus , ApplicationListener<ContextRefreshedEvent> {

    private Multimap<Class<?>, SynchronousEventBus.Subscriber> subscribers = create();

    @Override
    public void register(Object eventHandler, Method method) {
        if (!isAlreadyRegistered(eventHandler, method)) {
            assertMethodIsPublic(method);
            registerMethod(eventHandler, method);
        }
    }

    @Override
    public void unregister(Object eventHandler) {
        subscriptionsFor(eventHandler).forEach(entry -> subscribers.remove(entry.getKey(), entry.getValue()));
    }

    @Override
    public abstract void post(Object event);

    private boolean isAlreadyRegistered(Object handler, Method method) {
        return !filterEntries(subscribers, entry -> entry.getValue().handler == handler && entry.getValue().method == method).isEmpty();
    }

    private Collection<Map.Entry<Class<?>, SynchronousEventBus.Subscriber>> subscriptionsFor(Object handler) {
        return filterEntries(subscribers, entry -> entry.getValue().handler == handler).entries();
    }

    private void assertMethodIsPublic(Method method) {
        if (!isPublic(method.getModifiers())) {
            throw new IllegalStateException("A method with @Subscribe needs to be public! : " + method);
        }
    }

    private void registerMethod(Object handler, Method method) {
        subscribers.put(method.getParameterTypes()[0], new SynchronousEventBus.Subscriber(handler, method));
    }

    synchronized Collection<SynchronousEventBus.Subscriber> subscribersForEvent(Object event) {
        Class<?> clazz = event.getClass();
        List<SynchronousEventBus.Subscriber> subscribers = Lists.newArrayList();
        while (clazz != null) {
            subscriberForInterfaces(clazz, subscribers);
            subscribers.addAll(this.subscribers.get(clazz));
            clazz = clazz.getSuperclass();
        }
        return subscribers;
    }

    private void subscriberForInterfaces(Class<?> clazz, List<SynchronousEventBus.Subscriber> subscribers) {
        for (Class<?> interfaceClazz : clazz.getInterfaces()) {
            subscribers.addAll(this.subscribers.get(interfaceClazz));
        }
    }

    static class Subscriber {

        private final Object handler;
        private final Method method;

        Subscriber(Object handler, Method method) {
            this.handler = handler;
            this.method = method;
        }

        void handle(Object event) throws Throwable {
            if (isJdkDynamicProxy(handler)) {
                getInvocationHandler(handler).invoke(handler, method, new Object[]{event});
            } else {
                method.setAccessible(true);
                method.invoke(handler, event);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        post(ApplicationStarted.applicationStarted());
    }
}