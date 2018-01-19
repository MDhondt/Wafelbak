package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collection;
import java.util.HashSet;

class StreetData {

    private StreetId id;
    private ClusterId clusterId;
    private StringProperty name = new SimpleStringProperty();
    private Collection<CoordinateLine> coordinateLines = new HashSet<>();

    StreetData(Street street, Collection<CoordinateLine> coordinateLines) {
        id = street.id();
        clusterId = street.cluster().id();
        name.setValue(street.name());
        this.coordinateLines.addAll(coordinateLines);
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

    Collection<CoordinateLine> coordinateLines() {
        return new HashSet<>(coordinateLines);
    }
}