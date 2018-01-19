package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.util.OpenStreetMapUtils;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Component
public class MapPresenter extends Presenter<MapModel, MapView> {

    @Inject
    private OpenStreetMapUtils osmUtils;

    @PostConstruct
    void initModel() {
        model().setBorderOfRonse(osmUtils.borderOfRonse());
    }

    public void selectStreets(List<CoordinateLine> selectedStreets) {
        model().setSelectedStreets(selectedStreets);
    }
}