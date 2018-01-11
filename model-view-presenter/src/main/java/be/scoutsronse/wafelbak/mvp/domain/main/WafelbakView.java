package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Double.MAX_VALUE;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;
    private MapView mapView;
    private BorderPane borderPane;

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
        borderPane = new BorderPane();
        borderPane.setCenter(createMapView());
        borderPane.setLeft(createLeftTools());

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
//        primaryStage.setMaximized(true);
    }

    private Node createLeftTools() {
        Accordion leftTools = new Accordion();
        VBox boxForButton = new VBox(20);
        boxForButton.getChildren().add( new Button("click me"));
        TitledPane titledPane = new TitledPane("test title pane",boxForButton);
        leftTools.getPanes().add(titledPane);
        leftTools.setExpandedPane(titledPane);
        leftTools.setMinWidth(250);

        return leftTools;
    }

    private MapView createMapView() {
        mapView = new MapView();
        mapView.setCustomMapviewCssURL(getClass().getResource("/mapview.css"));
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setZoom(14);
                mapView.setCenter(new Coordinate(50.749077, 3.599492));
            }
        });
        mapView.initialize();
        mapView.setMaxSize(MAX_VALUE, MAX_VALUE);
        mapView.setMinSize(750,750);

        return mapView;
    }
}