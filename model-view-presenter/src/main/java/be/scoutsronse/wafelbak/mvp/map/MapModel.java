package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.Model;
import com.sothawo.mapjfx.CoordinateLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MapModel extends Model<MapView> {

    private CoordinateLine borderOfRonse;
    private List<CoordinateLine> selectedStreets = new ArrayList<>();

    public MapModel(MapView view) {
        super(view);
        view().getMap().initializedProperty().addListener((observable, oldValue, newValue) -> {
            borderOfRonse.setColor(RED);
            view().getMap().addCoordinateLine(borderOfRonse);
            borderOfRonse.setVisible(true);
        });
    }

    void setBorderOfRonse(CoordinateLine borderOfRonse) {
        this.borderOfRonse = borderOfRonse;
    }

    void setSelectedStreets(Collection<CoordinateLine> selectedStreets) {
        if (!this.selectedStreets.isEmpty()) {
            for (CoordinateLine street : this.selectedStreets) {
                view().getMap().removeCoordinateLine(street);
            }
        }
        this.selectedStreets.clear();
        for (CoordinateLine selectedStreet : selectedStreets) {
            selectedStreet.setColor(BLUE);
            view().getMap().addCoordinateLine(selectedStreet);
            selectedStreet.setVisible(true);
            this.selectedStreets.add(selectedStreet);
        }
    }
}