package be.scoutsronse.wafelbak.mvp.main;

import be.scoutsronse.wafelbak.mvp.View;
import be.scoutsronse.wafelbak.mvp.common.AccordionPane;
import com.sothawo.mapjfx.MapView;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage mainStage;
    private Scene scene;
    private BorderPane mainPane;
    private Accordion leftTools;
    private Map<TitledPane, ChangeListener<TitledPane>> accordionPaneCollapseListeners;
    private Map<TitledPane, ChangeListener<TitledPane>> accordionPaneExpansionListeners;

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
        accordionPaneCollapseListeners = new HashMap<>();
        accordionPaneExpansionListeners = new HashMap<>();

        Accordion leftTools = new Accordion();
        leftTools.expandedPaneProperty().addListener((observable, oldValue, newValue) -> {
            if (accordionPaneCollapseListeners.containsKey(oldValue)) {
                accordionPaneCollapseListeners.get(oldValue).changed(observable, oldValue, newValue);
            }
            if (accordionPaneExpansionListeners.containsKey(newValue)) {
                accordionPaneExpansionListeners.get(newValue).changed(observable, oldValue, newValue);
            }
        });
        leftTools.setMinWidth(250);

        return leftTools;
    }

    void addLeftTools(AccordionPane... panes) {
        for (AccordionPane pane : panes) {
            accordionPaneCollapseListeners.put(pane.pane(), pane.collapseListener());
            accordionPaneExpansionListeners.put(pane.pane(), pane.expansionListener());
            leftTools.getPanes().add(pane.pane());
        }
        leftTools.setExpandedPane(panes[0].pane());
        mainPane.setLeft(leftTools);
    }

    void addMap(MapView map) {
        mainPane.setCenter(map);
    }

    Stage mainStage() {
        return mainStage;
    }
}