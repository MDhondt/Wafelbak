package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.STREET_OVERVIEW_TITLE;
import static javafx.collections.FXCollections.observableArrayList;

public class StreetOverviewModel extends Model<StreetOverviewView> {

    private StringProperty title = new SimpleStringProperty();
    private ObservableList<StreetDto> streetDtos = observableArrayList();
    private ObservableList<ClusterData> clusters = observableArrayList();
    private ObservableList<StreetData> streets = observableArrayList();

    public StreetOverviewModel(StreetOverviewView view) {
        super(view);
    }

    public void bindViewToModel(List<StreetDto> streets) {
        view().streetOverviewTable().setItems(this.streetDtos);
        view().overviewTable().setItems(this.clusters);
        view().titleProperty().bind(title);

        title.setValue(view().message(STREET_OVERVIEW_TITLE));
        this.streetDtos.setAll(streets);
    }

    public void setClusters(Collection<ClusterData> clusters, Collection<StreetData> streets) {
        this.clusters.setAll(clusters);
        this.streets.setAll(streets);
    }
}