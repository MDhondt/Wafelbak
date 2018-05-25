package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.overview.streets.StreetOverviewPresenter;
import be.scoutsronse.wafelbak.mvp.util.OpenStreetMapUtils;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;

@Component
public class MapPresenter extends Presenter<MapModel, MapView> {

    @Inject
    private OpenStreetMapUtils osmUtils;
    @Inject
    private StreetOverviewPresenter streetOverviewPresenter;

    @PostConstruct
    void initModel() {
        model().setBorderOfRonse(osmUtils.borderOfRonse());
    }

    public void selectStreets(Collection<CoordinateLine> selectedStreets, Color color) {
        if (selectedStreets.isEmpty()) {
            streetOverviewPresenter.clearSelection();
        }
        model().setSelectedStreets(selectedStreets, color);
    }

    public void changeBorder(Color color, boolean visible) {
        model().changeBorder(color, visible);
    }
}