package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.osm.domain.Way;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static java.util.Optional.ofNullable;

class StreetDto {

    private static final String UNKNOWN_NAME = "Unknown name";

    private StringProperty name = new SimpleStringProperty();
    private CoordinateLine coordinateLine;

    StreetDto(Way way, CoordinateLine coordinateLine) {
        name.setValue(ofNullable(way.tags()).map(tags -> tags.get("name")).orElse(UNKNOWN_NAME));
        this.coordinateLine = coordinateLine;
    }

    StringProperty name() {
        return name;
    }

    CoordinateLine coordinateLine() {
        return coordinateLine;
    }
}