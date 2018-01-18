package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class StreetData {

    private StreetId id;
    private ClusterId clusterId;
    private StringProperty name = new SimpleStringProperty();

    StreetData(Street street) {
        id = street.id();
        clusterId = street.cluster().id();
        name.setValue(street.name());
    }

    StreetId id() {
        return id;
    }

    ClusterId clusterId() {
        return clusterId;
    }

    StringProperty name() {
        return name;
    }
}