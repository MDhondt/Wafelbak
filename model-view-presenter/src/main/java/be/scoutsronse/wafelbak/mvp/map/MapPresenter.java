package be.scoutsronse.wafelbak.mvp.map;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.main.OSMUtils;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class MapPresenter extends Presenter<MapModel, MapView> {

    @Inject
    private OSMUtils osmUtils;

    @PostConstruct
    void initModel() {
        model().setBorderOfRonse(osmUtils.borderOfRonse());
    }

    public void selectStreet(CoordinateLine selectedStreet) {
        model().setSelectedStreet(selectedStreet);
    }
}