package be.scoutsronse.wafelbak.mvp.main;

import be.scoutsronse.wafelbak.mvp.View;
import com.sothawo.mapjfx.MapView;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage mainStage;
    private Scene scene;
    private BorderPane mainPane;
    private Accordion leftTools;

    public WafelbakView(WafelbakPresenter presenter) {
        super(presenter);

        mainPane = new BorderPane();
        leftTools = createLeftTools();
        scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
    }

    public void show() {
        populateStage();
        mainStage.show();
    }

    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void populateStage() {
        mainStage.setScene(scene);
        mainStage.setMaximized(true);
    }

    private Accordion createLeftTools() {
        Accordion leftTools = new Accordion();
        leftTools.setMinWidth(250);

        return leftTools;
    }

//    private TitledPane createStartSale() {
//        VBox startSale = new VBox();
//
//        return new TitledPane(message(START_SALE_TITLE), startSale);
//    }
//
//    private TitledPane createSettings() {
//        VBox settings = new VBox();
//        CheckBox emphasizeRonse = new CheckBox(message(EMPHASIZE_RONSE));
//        settings.getChildren().add(emphasizeRonse);
//        ronseBorder = loadCoordinateLine(getClass().getResource("/ronseBorder.csv")).orElse(new CoordinateLine()).setColor(Color.MAGENTA);
//        emphasizeRonse.selectedProperty().bindBidirectional(ronseBorder.visibleProperty());
//
//        return new TitledPane(message(SETTINGS), settings);
//    }

    public void addLeftTools(TitledPane... panes) {
        leftTools.getPanes().setAll(panes);
        leftTools.setExpandedPane(panes[0]);
        mainPane.setLeft(leftTools);
    }

    public void addMap(MapView map) {
        mainPane.setCenter(map);
    }
}