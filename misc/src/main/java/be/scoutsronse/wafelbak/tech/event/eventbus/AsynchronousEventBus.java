package be.scoutsronse.wafelbak.tech.event.eventbus;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

@Component
@Asynchronous
public class AsynchronousEventBus extends EventBusImpl {

    private ExecutorService executorService;

    @PostConstruct
    public void construct() {
        executorService = newCachedThreadPool();
    }

    @Override
    public void post(Object event) {
        subscribersForEvent(event).forEach(subscriber -> {
            try {
                executorService.submit(() -> {
                    try {
                        subscriber.handle(event);
                    } catch (Throwable t) {
                    }
                });
            } catch (Throwable t) {
            }
        });
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}