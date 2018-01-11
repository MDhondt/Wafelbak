package be.scoutsronse.wafelbak.application;

import be.scoutsronse.wafelbak.mvp.domain.presenter.WafelbakPresenter;
import be.scoutsronse.wafelbak.tech.event.eventbus.AsynchronousEventBus;
import be.scoutsronse.wafelbak.tech.event.eventbus.PrioritizedEventBus;
import be.scoutsronse.wafelbak.tech.event.eventbus.Subscribe;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static be.scoutsronse.wafelbak.tech.reflection.Reflection.findAllMethodsWithAnnotation;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Wafelbak extends Application {

    private final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WafelbakConfig.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        registerEventHandlers();
        startApplication(primaryStage);
    }

    private void registerEventHandlers() {
        PrioritizedEventBus eventBus = applicationContext.getBean(PrioritizedEventBus.class);
        AsynchronousEventBus asyncEventBus = applicationContext.getBean(AsynchronousEventBus.class);

        Map<Object, Collection<Method>> eventHandlers = eventHandlers(stream(applicationContext.getBeanDefinitionNames()).map(beanName -> applicationContext.getBean(beanName)).collect(toList()));
        List<Integer> priorities = eventHandlers.values().stream()
                .flatMap(Collection::stream)
                .map(method -> method.getAnnotation(Subscribe.class))
                .map(Subscribe::priority)
                .distinct()
                .sorted()
                .collect(toList());
        eventBus.initialisePriorities(priorities);

        for (Object eventHandlingBean : eventHandlers.keySet()) {
            for (Method method : eventHandlers.get(eventHandlingBean)) {
                eventBus.register(eventHandlingBean, method);
                asyncEventBus.register(eventHandlingBean, method);
            }
        }
    }

    private Map<Object, Collection<Method>> eventHandlers(List<Object> beans) {
        return beans.stream()
                .filter(bean -> !findAllMethodsWithAnnotation(bean, Subscribe.class).isEmpty())
                .collect(toMap(identity(), bean -> findAllMethodsWithAnnotation(bean, Subscribe.class)));
    }

    private void startApplication(Stage primaryStage) {
        WafelbakPresenter presenter = applicationContext.getBean(WafelbakPresenter.class);
        presenter.view().setStage(primaryStage);
        presenter.view().show();
    }
}