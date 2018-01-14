package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateLine;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.*;
import static be.scoutsronse.wafelbak.mvp.util.MapViewUtils.loadCoordinateLine;
import static java.lang.Double.MAX_VALUE;
import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;
    private MapView mapView;
    private CoordinateLine ronseBorder;

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
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createMapView());
        borderPane.setLeft(createLeftTools());

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        primaryStage.setMaximized(true);
    }

    private Node createLeftTools() {
        Accordion leftTools = new Accordion();

        TitledPane initPane = createStreetOverview();
        leftTools.getPanes().addAll(initPane, createStartSale(), createSettings());
        leftTools.setExpandedPane(initPane);
        leftTools.setMinWidth(250);

        return leftTools;
    }

    private TitledPane createStreetOverview() {
        VBox streetOverview = new VBox();
        TableView table = new TableView();
        streetOverview.getChildren().addAll(table);

        return new TitledPane(message(STREET_OVERVIEW_TITLE), streetOverview);
    }

    private TitledPane createStartSale() {
        VBox startSale = new VBox();

        return new TitledPane(message(START_SALE_TITLE), startSale);
    }

    private TitledPane createSettings() {
        VBox settings = new VBox();
        CheckBox emphasizeRonse = new CheckBox(message(EMPHASIZE_RONSE));
        settings.getChildren().add(emphasizeRonse);
        ronseBorder = loadCoordinateLine(getClass().getResource("/coordinates/Ronse.csv")).orElse(new CoordinateLine()).setColor(Color.MAGENTA);
        emphasizeRonse.selectedProperty().bindBidirectional(ronseBorder.visibleProperty());

        return new TitledPane(message(SETTINGS), settings);
    }

    private MapView createMapView() {
        mapView = new MapView();
        mapView.setCustomMapviewCssURL(getClass().getResource("/css/mapview.css"));
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setZoom(14);
                mapView.setCenter(new Coordinate(50.749077, 3.599492));
                mapView.addCoordinateLine(ronseBorder);
            }
        });

        OfflineCache offlineCache = mapView.getOfflineCache();
        String cacheDir = getProperty("user.home") + "/Wafelbak/cache";
        try {
            Files.createDirectories(get(cacheDir));
            offlineCache.setCacheDirectory(cacheDir);
            offlineCache.setActive(true);
        } catch (IOException e) {
        }

        mapView.initialize();
        mapView.setMaxSize(MAX_VALUE, MAX_VALUE);
        mapView.setMinSize(750,750);

        return mapView;
    }
}