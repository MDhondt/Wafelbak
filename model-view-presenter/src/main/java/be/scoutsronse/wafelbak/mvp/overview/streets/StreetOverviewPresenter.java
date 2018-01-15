package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.main.OSMUtils;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.osm.OpenStreetMapService;
import be.scoutsronse.wafelbak.osm.domain.OSM;
import be.scoutsronse.wafelbak.osm.domain.Way;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class StreetOverviewPresenter extends Presenter<StreetOverviewModel, StreetOverviewView> {

    @Inject
    private OpenStreetMapService osmService;
    @Inject
    private OSMUtils osmUtils;
    @Inject
    private MapPresenter mapPresenter;

    @PostConstruct
    void initStreets() {
        OSM ronse = osmService.getRonse();
        Map<Way, CoordinateLine> streetsOfRonse = osmUtils.getStreetsOfRonse(ronse);
        List<StreetDto> streetDtosOfRonse = streetsOfRonse.entrySet().stream().map(entry -> new StreetDto(entry.getKey(), entry.getValue())).collect(toList());
        model().setStreets(streetDtosOfRonse);
    }

    public void selectStreet(CoordinateLine coordinateLine) {
        mapPresenter.selectStreet(coordinateLine);
    }
}