package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.List;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.STREET_OVERVIEW_TITLE;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

public class StreetOverviewModel extends Model<StreetOverviewView> {

    private StringProperty title = new SimpleStringProperty();
    private ObservableList<StreetDto> streetDtos = observableArrayList();
    private ObservableList<ClusterData> clusters = observableArrayList();

    public StreetOverviewModel(StreetOverviewView view) {
        super(view);
    }

    public void bindViewToModel(List<StreetDto> streets) {
        view().streetOverviewTable().setItems(this.streetDtos);
        view().titleProperty().bind(title);

        title.setValue(view().message(STREET_OVERVIEW_TITLE));
        this.streetDtos.setAll(streets);
    }

    void setClusters(Collection<ClusterData> clusters) {
        this.clusters.setAll(clusters);
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
    }

    void clearSelection() {
        view().clearSelection();
    }
}