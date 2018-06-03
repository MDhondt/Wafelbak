package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.controlsfx.control.textfield.TextFields.createClearableTextField;

public class SearchableClusterTreeView extends VBox {

    private TextField searchBox;
    private TreeView<ClusterItem> treeView;
    private Collection<ClusterDto> allClusters;
    private Collection<ClusterDto> visibleClusters;

    public SearchableClusterTreeView() {
        super(5);

        searchBox = createClearableTextField();
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchInput = newValue == null ? "" : newValue;
            // TODO: search
        });

        treeView = new TreeView<>(new TreeItem<>());
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                be.scoutsronse.wafelbak.mvp.overview.streets.ClusterItem selectedItem = newValue.getValue();
                if (selectedItem instanceof be.scoutsronse.wafelbak.mvp.overview.streets.ClusterData) {
                    presenter().selectStreets(((be.scoutsronse.wafelbak.mvp.overview.streets.ClusterData) selectedItem).streets().stream().map(be.scoutsronse.wafelbak.mvp.overview.streets.StreetData::coordinateLines).flatMap(Collection::stream).collect(toList()));
                } else if (selectedItem instanceof be.scoutsronse.wafelbak.mvp.overview.streets.StreetData) {
                    presenter().selectStreets(((be.scoutsronse.wafelbak.mvp.overview.streets.StreetData) selectedItem).coordinateLines());
                }
            } else {
                presenter().selectStreets(emptyList());
            }
        });
    }
}