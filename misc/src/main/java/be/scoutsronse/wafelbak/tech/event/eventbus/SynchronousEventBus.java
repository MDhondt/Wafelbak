package be.scoutsronse.wafelbak.tech.event.eventbus;

import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import be.scoutsronse.wafelbak.tech.reflection.Reflection;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.eventbus.Subscribe;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Proxy.getInvocationHandler;

@Component
public class SynchronousEventBus implements EventBus, ApplicationListener<ContextRefreshedEvent> {

    private Multimap<Class<?>, Subscriber> subscribers = ArrayListMultimap.create();

    @Override
    public void register(Object eventHandler) {
        if (!isAlreadyRegistered(eventHandler)) {
            Iterable<Method> methods = Reflection.findAllMethodsWithAnnotation(eventHandler, Subscribe.class);
            for (Method method : methods) {
                assertMethodIsPublic(method);
                registerMethod(eventHandler, method);
            }
        }
    }

    @Override
    public void unregister(Object eventHandler) {
        subscriptionsFor(eventHandler).forEach(entry -> subscribers.remove(entry.getKey(), entry.getValue()));
    }

    @Override
    public void post(Object event) {
        subscribersForEvent(event).forEach(subscriber -> {
            try {
                subscriber.handle(event);
            } catch (Throwable t) {

            }
        });
    }

    private boolean isAlreadyRegistered(Object handler) {
        return !subscriptionsFor(handler).isEmpty();
    }

    private Collection<Map.Entry<Class<?>, Subscriber>> subscriptionsFor(Object handler) {
        return Multimaps.filterEntries(subscribers, entry -> entry.getValue().handler == handler).entries();
    }

    private void assertMethodIsPublic(Method method) {
        if (!isPublic(method.getModifiers())) {
            throw new IllegalStateException("A method with @Subscribe needs to be public! : " + method);
        }
    }

    private void registerMethod(Object handler, Method method) {
        subscribers.put(method.getParameterTypes()[0], new Subscriber(handler, method));
    }

    private synchronized Collection<Subscriber> subscribersForEvent(Object event) {
        Class<?> clazz = event.getClass();
        List<Subscriber> subscribers = Lists.newArrayList();
        while (clazz != null) {
            subscriberForInterfaces(clazz, subscribers);
            subscribers.addAll(this.subscribers.get(clazz));
            clazz = clazz.getSuperclass();
        }
        return subscribers;
    }

    private void subscriberForInterfaces(Class<?> clazz, List<Subscriber> subscribers) {
        for (Class<?> interfaceClazz : clazz.getInterfaces()) {
            subscribers.addAll(this.subscribers.get(interfaceClazz));
        }
    }

    private static class Subscriber {

        private final Object handler;
        private final Method method;

        Subscriber(Object handler, Method method) {
            this.handler = handler;
            this.method = method;
        }

        void handle(Object event) throws Throwable {
            if (AopUtils.isJdkDynamicProxy(handler)) {
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