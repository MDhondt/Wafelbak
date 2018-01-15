package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.STREET_OVERVIEW_TITLE;
import static javafx.collections.FXCollections.observableArrayList;

public class StreetOverviewModel extends Model<StreetOverviewView> {

    private static StringProperty title = new SimpleStringProperty();
    private static ObservableList<StreetDto> streets = observableArrayList();

    public StreetOverviewModel(StreetOverviewView view) {
        super(view);
    }

    @Override
    protected void bindViewToModel() {
        view().streetOverviewTable().setItems(streets);
        view().titleProperty().bind(title);
    }

    void setStreets(List<StreetDto> streets) {
        this.streets.setAll(streets);
        title.setValue(view().message(STREET_OVERVIEW_TITLE));
    }
}