package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.geometry.Pos.TOP_CENTER;
import static org.controlsfx.control.textfield.TextFields.createClearableTextField;

public class SearchableClusterTreeView extends VBox {

    TextField searchBox;
    TreeView<ClusterItem> treeView;
    Collection<ClusterDto> allClusters;
    TreeSet<ClusterDto> visibleClusters = new TreeSet<>(comparing(cluster -> cluster.name));

    public SearchableClusterTreeView(Consumer<Collection<StreetId>> selectionConsumer) {
        super(5);

        searchBox = createClearableTextField();
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchInput = newValue == null ? "" : newValue;
            search(searchInput);
        });

        treeView = new TreeView<>(new TreeItem<>());
        treeView.setShowRoot(false);
        if (selectionConsumer != null) {
            setSelectionConsumer(selectionConsumer);
        }

        getChildren().addAll(searchBox, treeView);
        setAlignment(TOP_CENTER);
        treeView.prefHeightProperty().bind(this.heightProperty());
        treeView.prefWidthProperty().bind(this.widthProperty());
        searchBox.minWidthProperty().bind(treeView.widthProperty());
        searchBox.prefWidthProperty().bind(treeView.widthProperty());
        searchBox.maxWidthProperty().bind(treeView.widthProperty());
    }

    public void setSelectionConsumer(Consumer<Collection<StreetId>> selectionConsumer) {
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

    public void setContentOpacity(double opacity) {
        this.searchBox.setOpacity(opacity);
        this.treeView.setOpacity(opacity);
    }

    public void setAllowedDragSources(Collection<Triple<SearchableClusterTreeView, Predicate<ClusterDto>, Consumer<ClusterDto>>> allowedDragSources, Consumer<ClusterId> doubleClickConsumer) {
        treeView.setCellFactory(new DragDropCellFactory(this, allowedDragSources, doubleClickConsumer));
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

    void search(String searchInput) {
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

    public ClusterId getSelectedClusterId() {
        ClusterItem clusterItem = treeView.getSelectionModel().getSelectedItem().getValue();
        if (clusterItem instanceof ClusterData) {
            return ((ClusterData) clusterItem).getId();
        }
        return null;
    }
}