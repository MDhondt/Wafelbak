package be.scoutsronse.wafelbak.model;

import be.scoutsronse.wafelbak.view.MapView;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.tuple.Pair;

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

    public void setSelectedStreets(Collection<Pair<Collection<CoordinateLine>, Color>> streetGroups) {
        view.deselectStreets(selectedStreets);
        selectedStreets.clear();
        streetGroups.forEach(group -> view.selectStreets(group.getLeft(), group.getRight()));
        streetGroups.forEach(group -> selectedStreets.addAll(group.getLeft()));
    }
}