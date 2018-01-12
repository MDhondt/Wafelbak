package be.scoutsronse.wafelbak.mvp.domain.main;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.MessageSource;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.Double.MAX_VALUE;
import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;
    private MapView mapView;
    private BorderPane borderPane;

    public WafelbakView(WafelbakPresenter presenter, MessageSource messageSource) {
        super(presenter, messageSource);
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
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        primaryStage.setMaximized(true);
    }

    private Node createLeftTools() {
        Accordion leftTools = new Accordion();

        TitledPane initPane = createStreetOverview();
        leftTools.getPanes().addAll(initPane, createStartSale());
        leftTools.setExpandedPane(initPane);
        leftTools.setMinWidth(250);

        return leftTools;
    }

    private TitledPane createStreetOverview() {
        VBox streetOverview = new VBox();
        TableView table = new TableView();
        streetOverview.getChildren().addAll(table);

        return new TitledPane("title", streetOverview);
//        return new TitledPane(messageSource().getMessage("STREET_OVERVIEW_TITLE", null, null), streetOverview);
    }

    private TitledPane createStartSale() {
        VBox startSale = new VBox();

        return new TitledPane("start sale", startSale);
    }

    private MapView createMapView() {
        mapView = new MapView();
        mapView.setCustomMapviewCssURL(getClass().getResource("/css/mapview.css"));
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setZoom(14);
                mapView.setCenter(new Coordinate(50.749077, 3.599492));
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