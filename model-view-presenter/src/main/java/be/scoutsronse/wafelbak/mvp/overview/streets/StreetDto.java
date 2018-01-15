package be.scoutsronse.wafelbak.mvp.overview.streets;

import com.sothawo.mapjfx.CoordinateLine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

class StreetDto {

    private static final String UNKNOWN_NAME = "Unknown name";

    private StringProperty name = new SimpleStringProperty();
    private List<CoordinateLine> coordinateLines;

    StreetDto(String name, List<CoordinateLine> coordinateLines) {
        this.name.setValue(name);
        this.coordinateLines = coordinateLines;
    }

    StringProperty name() {
        return name;
    }

    List<CoordinateLine> coordinateLines() {
        return coordinateLines;
    }
}