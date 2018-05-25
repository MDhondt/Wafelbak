package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.controlsfx.control.textfield.TextFields.createClearableTextField;

public class StreetOverviewView extends View<StreetOverviewPresenter> {

    private TitledPane pane;
    private TreeView<ClusterItem> clusterTree;
    private TextField searchBox;

    public StreetOverviewView(StreetOverviewPresenter presenter) {
        super(presenter);

        searchBox = createClearableTextField();
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchInput = newValue == null ? "" : newValue;
            search(searchInput);
        });
        createClusterTree();

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(searchBox, clusterTree);
        clusterTree.prefHeightProperty().bind(vBox.heightProperty());
        searchBox.prefWidthProperty().bind(clusterTree.widthProperty());

        pane = new TitledPane();
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
                presenter().selectStreets(emptyList());
            }
        });
    }

    TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }

    TreeItem<ClusterItem> clusterRoot() {
        return clusterTree.getRoot();
    }

    void clearSelection() {
        searchBox.clear();
        clusterTree.getSelectionModel().clearSelection();
    }

    private void search(String searchInput) {
        presenter().search(searchInput);
    }
}