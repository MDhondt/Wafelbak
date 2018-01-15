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

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

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
        Map<CoordinateLine, Way> streetsByLine = streetsOfRonse.keySet().stream().collect(toMap(streetsOfRonse::get, identity()));
        Map<String, List<CoordinateLine>> streetsByName = streetsOfRonse.values().stream().collect(groupingBy(street -> ofNullable(streetsByLine.get(street).tags()).map(tags -> tags.get("name")).orElse("Unknown name")));
        List<StreetDto> streetDtosOfRonse = streetsByName.entrySet().stream().map(entry -> new StreetDto(entry.getKey(), entry.getValue())).collect(toList());
        model().setStreets(streetDtosOfRonse);
    }

    public void selectStreets(List<CoordinateLine> coordinateLines) {
        mapPresenter.selectStreets(coordinateLines);
    }
}