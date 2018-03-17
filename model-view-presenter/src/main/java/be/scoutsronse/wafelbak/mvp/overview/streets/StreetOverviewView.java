package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

public class StreetOverviewView extends View<StreetOverviewPresenter> {

    private TitledPane pane;
    private TreeView<ClusterItem> clusterTree;

    public StreetOverviewView(StreetOverviewPresenter presenter) {
        super(presenter);
        pane = new TitledPane();
        createClusterTree();
        TableColumn<StreetDto, String> streetNameColumn = new TableColumn<>();
        streetNameColumn.setCellValueFactory(cellData -> cellData.getValue().name());
        VBox vBox = new VBox();
        vBox.getChildren().addAll(clusterTree);
        pane.setContent(vBox);
    }

    private void createClusterTree() {
        clusterTree = new TreeView<>(new TreeItem<>());
        clusterTree.setShowRoot(false);
        clusterTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ClusterItem selectedItem = newValue.getValue();
                if (selectedItem instanceof ClusterData) {
                    presenter().selectStreets(((ClusterData) selectedItem).streets().stream().map(StreetData::coordinateLines).flatMap(Collection::stream).collect(toList()));
                } else if (selectedItem instanceof StreetData) {
                    presenter().selectStreets(((StreetData) selectedItem).coordinateLines());
                }
            } else {
                presenter().selectStreets(Collections.emptyList());
            }
        });
    }

    public TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }

    TreeItem<ClusterItem> clusterRoot() {
        return clusterTree.getRoot();
    }

    void clearSelection() {
        clusterTree.getSelectionModel().clearSelection();
    }
}