package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.geometry.Pos.TOP_CENTER;
import static org.controlsfx.control.textfield.TextFields.createClearableTextField;

public class SearchableClusterTreeView extends VBox {

    private TextField searchBox;
    private TreeView<ClusterItem> treeView;
    private Collection<ClusterDto> allClusters;
    private TreeSet<ClusterDto> visibleClusters = new TreeSet<>(comparing(cluster -> cluster.name));

    public SearchableClusterTreeView(Consumer<Collection<StreetId>> selectionConsumer) {
        super(5);

        searchBox = createClearableTextField();
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchInput = newValue == null ? "" : newValue;
            search(searchInput);
        });

        treeView = new TreeView<>(new TreeItem<>());
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ClusterItem selectedItem = newValue.getValue();
                if (selectedItem instanceof ClusterData) {
                    selectionConsumer.accept(visibleClusters.stream()
                                                            .filter(cluster -> cluster.id.equals(((ClusterData) selectedItem).getId()))
                                                            .findFirst()
                                                            .map(cluster -> cluster.streets.stream().map(street -> street.id).collect(toList()))
                                                            .orElse(emptyList()));
                } else if (selectedItem instanceof StreetData) {
                    selectionConsumer.accept(singletonList(((StreetData) selectedItem).getId()));
                }
            } else {
                selectionConsumer.accept(emptyList());
            }
        });

        getChildren().addAll(searchBox, treeView);
        setAlignment(TOP_CENTER);
        treeView.prefHeightProperty().bind(this.heightProperty());
        treeView.prefWidthProperty().bind(this.widthProperty());
        searchBox.minWidthProperty().bind(treeView.widthProperty());
        searchBox.prefWidthProperty().bind(treeView.widthProperty());
        searchBox.maxWidthProperty().bind(treeView.widthProperty());
    }

    public void setContent(Collection<ClusterDto> clusters) {
        this.allClusters = new ArrayList<>(clusters);
        this.visibleClusters = new TreeSet<>(clusters);
        setVisibleClusters("DO_NOT_EXPAND");
    }

    public void clearSelection() {
        this.searchBox.clear();
        this.treeView.getSelectionModel().clearSelection();
    }

    private void setVisibleClusters(String expansionName) {
        treeView.getRoot().getChildren().clear();
        visibleClusters.forEach(visibleCluster -> {
            TreeItem<ClusterItem> clusterData = new TreeItem<>(new ClusterData(visibleCluster.name, visibleCluster.id));
            visibleCluster.streets.stream()
                                  .sorted(comparing(street -> street.name))
                                  .map(street -> new TreeItem<ClusterItem>(new StreetData(street.name, street.id)))
                                  .forEach(streetData -> clusterData.getChildren().add(streetData));
            treeView.getRoot().getChildren().add(clusterData);
        });
        if (expansionName != null && !expansionName.isEmpty()) {
            treeView.getRoot().getChildren().forEach(clusterItem -> {
                if (clusterItem.getChildren().stream().map(TreeItem::getValue).map(ClusterItem::getName).anyMatch(street -> street.toLowerCase().contains(expansionName.toLowerCase()))) {
                    clusterItem.setExpanded(true);
                }
            });
        }
    }

    private void search(String searchInput) {
        visibleClusters.clear();
        for (ClusterDto cluster : allClusters) {
            if (cluster.name.toLowerCase().contains(searchInput.toLowerCase())) {
                visibleClusters.add(cluster);
            } else if (cluster.streets.stream().map(street -> street.name.toLowerCase()).anyMatch(street -> street.contains(searchInput.toLowerCase()))) {
                visibleClusters.add(cluster);
            }
        }
        setVisibleClusters(searchInput);
    }
}