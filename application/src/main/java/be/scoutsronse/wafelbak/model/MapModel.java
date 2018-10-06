package be.scoutsronse.wafelbak.model;

import be.scoutsronse.wafelbak.view.MapView;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MapModel {

    private CoordinateLine borderOfRonse;
    private List<CoordinateLine> selectedStreets = new ArrayList<>();
    private MapView view;

    public MapModel(CoordinateLine borderOfRonse) {
        this.borderOfRonse = borderOfRonse;
    }

    public CoordinateLine borderOfRonse() {
        return borderOfRonse;
    }

    public void setView(MapView view) {
        this.view = view;
    }

    public void setSelectedStreets(Collection<CoordinateLine> streets, Color color) {
        view.deselectStreets(selectedStreets);
        selectedStreets.clear();
        view.selectStreets(streets, color);
        selectedStreets.addAll(streets);
    }
}