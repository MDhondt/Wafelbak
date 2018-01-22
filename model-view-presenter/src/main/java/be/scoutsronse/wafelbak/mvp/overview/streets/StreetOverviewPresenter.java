package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.util.OpenStreetMapUtils;
import be.scoutsronse.wafelbak.mvp.way.WayService;
import be.scoutsronse.wafelbak.osm.OpenStreetMapService;
import be.scoutsronse.wafelbak.osm.domain.OSM;
import be.scoutsronse.wafelbak.osm.domain.Way;
import be.scoutsronse.wafelbak.repository.api.ClusterRepository;
import be.scoutsronse.wafelbak.tech.util.Collectors;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static be.scoutsronse.wafelbak.domain.id.WayId.aWayId;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Component
public class StreetOverviewPresenter extends Presenter<StreetOverviewModel, StreetOverviewView> {

    @Inject
    private OpenStreetMapService osmService;
    @Inject
    private OpenStreetMapUtils osmUtils;
    @Inject
    private MapPresenter mapPresenter;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private WayService wayService;

    @PostConstruct
    void initStreets() {
        OSM ronse = osmService.getRonse();
        Map<Way, CoordinateLine> streetsOfRonse = osmUtils.getStreetsOfRonse(ronse);
        Map<CoordinateLine, Way> streetsByLine = streetsOfRonse.keySet().stream().collect(toMap(streetsOfRonse::get, identity()));
        Map<String, List<CoordinateLine>> streetsByName = streetsOfRonse.values().stream().collect(groupingBy(street -> ofNullable(streetsByLine.get(street).tags()).map(tags -> tags.get("name")).orElse("Unknown name")));
        List<StreetDto> streetDtosOfRonse = streetsByName.entrySet().stream().filter(entry -> entry.getValue().stream().flatMap(CoordinateLine::getCoordinateStream).count() > 2L).map(entry -> new StreetDto(entry.getKey(), entry.getValue())).collect(toList());
        model().bindViewToModel(streetDtosOfRonse);

        Collection<Cluster> clusters = clusterRepository.findAll();
        Map<ClusterId, ClusterData> clusterData = clusters.stream().map(ClusterData::new).collect(Collectors.toMapByKey(ClusterData::id));
        Map<ClusterId, List<StreetData>> streetData = clusters.stream().map(Cluster::streets).flatMap(Collection::stream).map(street -> new StreetData(street, wayService.findCoordinateLinesBy(street.wayIds()))).collect(groupingBy(StreetData::clusterId));
        clusterData.forEach((key, value) -> value.setStreets(streetData.get(key)));
        model().setClusters(clusterData.values());
    }

    public void selectStreets(Collection<CoordinateLine> coordinateLines) {
        mapPresenter.selectStreets(coordinateLines);
    }

    public void persist() {
        Street s1 = new Street("Hotondstraat (2)", asList(aWayId(79747186L), aWayId(114457690L)));
        Street s2 = new Street("Kanarieberg (5)", asList(aWayId(11837293L), aWayId(86502116L), aWayId(86502136L), aWayId(86502145L), aWayId(184230479L)));
        Street s3 = new Street("Wilgenstraat (2)", asList(aWayId(40032205L), aWayId(351206765L)));
        Street s4 = new Street("Kerkplein (2)", asList(aWayId(8117610L), aWayId(319960345L)));
        Cluster c1 = new Cluster("Cluster 1 test", asList(s1, s2));
        Cluster c2 = new Cluster("Testing nb 2", asList(s3, s4));
        clusterRepository.save(asList(c1, c2));
    }

    public void clearSelection() {
        model().clearSelection();
    }
}