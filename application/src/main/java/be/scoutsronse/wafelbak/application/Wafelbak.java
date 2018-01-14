package be.scoutsronse.wafelbak.application;

import be.scoutsronse.wafelbak.mvp.domain.main.WafelbakPresenter;
import be.scoutsronse.wafelbak.tech.event.eventbus.AsynchronousEventBus;
import be.scoutsronse.wafelbak.tech.event.eventbus.PrioritizedEventBus;
import be.scoutsronse.wafelbak.tech.event.eventbus.Subscribe;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import static javafx.concurrent.Worker.State.SUCCEEDED;
import static javafx.stage.Screen.getPrimary;
import static javafx.stage.StageStyle.DECORATED;

public class Wafelbak extends Application {

    private Image splash = new Image("/img/logo.png");
    private Image icon = new Image("/img/ico.png");

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private static final int SPLASH_WIDTH = 420;
    private static final int SPLASH_HEIGHT = 355;

    private AnnotationConfigApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(new ImageView(splash), loadProgress);
        splashLayout.setEffect(new DropShadow());
    }

    private void showSplash(Stage initStage, Task<Void> task) {
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.hide();

                startApplication();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        Rectangle2D screenBounds = getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(screenBounds.getMinX() + screenBounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(screenBounds.getMinY() + screenBounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
        initStage.getIcons().add(icon);
    }

    @Override
    public void start(Stage initStage) {
        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() {
                applicationContext = new AnnotationConfigApplicationContext(WafelbakConfig.class);
                registerEventHandlers();
                return null;
            }
        };

        showSplash(initStage, loadTask);
        new Thread(loadTask).start();
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

    private void startApplication() {
        WafelbakPresenter presenter = applicationContext.getBean(WafelbakPresenter.class);

        Stage mainStage = new Stage(DECORATED);
        mainStage.getIcons().add(icon);
        mainStage.setTitle("Scouts Ronse - Wafelbak");

        presenter.view().setStage(mainStage);
        presenter.view().show();
    }
}