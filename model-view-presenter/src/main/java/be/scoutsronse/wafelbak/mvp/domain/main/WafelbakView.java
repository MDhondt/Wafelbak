package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;
    private Button button;
    private MapView mapView;

    public WafelbakView(WafelbakPresenter presenter) {
        super(presenter);
    }

    public void show() {
        populateStage();
        primaryStage.show();
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void populateStage() {
        button = new Button("test");
        button.setOnAction(event -> presenter().test());

        mapView = new MapView();
        mapView.setCustomMapviewCssURL(getClass().getResource("/mapview.css"));
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });
        mapView.initialize();

        Scene scene = new Scene(mapView);
        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
    }

    private void afterMapIsInitialized() {
        mapView.setZoom(14);
        mapView.setCenter(new Coordinate(50.749077, 3.599492));
    }
}