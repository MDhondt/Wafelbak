package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.STREET_OVERVIEW_TITLE;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

public class StreetOverviewModel extends Model<StreetOverviewView> {

    private StringProperty title = new SimpleStringProperty();
    private ObservableList<ClusterData> clusters = observableArrayList();

    private List<ClusterData> allClusters = new ArrayList<>();

    public StreetOverviewModel(StreetOverviewView view) {
        super(view);
    }

    void bindViewToModel() {
        view().titleProperty().bind(title);
        title.setValue(view().message(STREET_OVERVIEW_TITLE));
    }

    void setAllClusters(Collection<ClusterData> clusters) {
        allClusters.clear();
        allClusters.addAll(clusters);
        setClusters(clusters, "DO_NOT_EXPAND");
    }

    Collection<ClusterData> getAllClusters() {
        return new ArrayList<>(allClusters);
    }

    void setClusters(Collection<ClusterData> clusters, String expandedName) {
        this.clusters.setAll(clusters);
        view().clusterRoot().getChildren().clear();
        clusters.stream().sorted(comparing(ClusterItem::toString)).forEach(cluster -> view().clusterRoot().getChildren().add(new TreeItem<>(cluster)));
        view().clusterRoot().getChildren()
                .forEach(clusterItem -> clusterItem.getChildren().addAll(
                        ((ClusterData) clusterItem.getValue())
                                .streets()
                                .stream()
                                .map(ClusterItem.class::cast)
                                .sorted(comparing(ClusterItem::toString))
                                .map(TreeItem::new)
                                .collect(toList())));
        if (expandedName != null && !expandedName.isEmpty()) {
            view().clusterRoot().getChildren().forEach(clusterItem -> {
                if (clusterItem.getChildren().stream().map(TreeItem::getValue).map(ClusterItem::toString).anyMatch(street -> street.toLowerCase().contains(expandedName.toLowerCase()))) {
                    clusterItem.setExpanded(true);
                }
            });
        }
    }

    void clearSelection() {
        view().clearSelection();
    }
}