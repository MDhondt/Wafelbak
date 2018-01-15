package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.Model;
import com.sothawo.mapjfx.CoordinateLine;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MapModel extends Model<MapView> {

    private CoordinateLine borderOfRonse;
    private CoordinateLine selectedStreet;

    public MapModel(MapView view) {
        super(view);
    }

    @Override
    protected void bindViewToModel() {
        view().getMap().initializedProperty().addListener((observable, oldValue, newValue) -> {
            borderOfRonse.setColor(RED);
            view().getMap().addCoordinateLine(borderOfRonse);
            borderOfRonse.setVisible(true);
        });
    }

    void setBorderOfRonse(CoordinateLine borderOfRonse) {
        this.borderOfRonse = borderOfRonse;
    }

    void setSelectedStreet(CoordinateLine selectedStreet) {
        if (this.selectedStreet != null) {
            view().getMap().removeCoordinateLine(this.selectedStreet);
        }
        this.selectedStreet = selectedStreet;
        selectedStreet.setColor(BLUE);
        view().getMap().addCoordinateLine(selectedStreet);
        selectedStreet.setVisible(true);
    }
}