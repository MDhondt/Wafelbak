package be.scoutsronse.wafelbak.application;

import be.scoutsronse.wafelbak.mvp.domain.presenter.WafelbakPresenter;
import be.scoutsronse.wafelbak.tech.event.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.List;

import static be.scoutsronse.wafelbak.tech.reflection.Reflection.findAllMethodsWithAnnotation;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Wafelbak extends Application {

    private final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WafelbakConfig.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        registerEventHandlers();
        startApplication(primaryStage);
    }

    private void registerEventHandlers() {
        EventBus eventBus = applicationContext.getBean(EventBus.class);
        eventHandlers(stream(applicationContext.getBeanDefinitionNames()).map(beanName -> applicationContext.getBean(beanName)).collect(toList())).forEach(eventBus::register);
    }

    private Collection<Object> eventHandlers(List<Object> beans) {
        return beans.stream().filter(object -> findAllMethodsWithAnnotation(object, Subscribe.class).size() > 0).collect(toList());
    }

    private void startApplication(Stage primaryStage) {
        WafelbakPresenter presenter = applicationContext.getBean(WafelbakPresenter.class);
        presenter.view().setStage(primaryStage);
        presenter.view().show();
    }
}