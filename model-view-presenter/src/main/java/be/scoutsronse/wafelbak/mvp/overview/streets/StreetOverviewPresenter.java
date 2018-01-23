package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.util.OpenStreetMapUtils;
import be.scoutsronse.wafelbak.mvp.way.WayService;
import be.scoutsronse.wafelbak.osm.OpenStreetMapService;
import be.scoutsronse.wafelbak.osm.domain.OSM;
import be.scoutsronse.wafelbak.osm.domain.Way;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.tech.util.Collectors;
import com.sothawo.mapjfx.CoordinateLine;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Component
@DependsOn("clusterInitializer")
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

    private Map<String, List<Long>> wayIdsByStreet;

    @PostConstruct
    void initStreets() {
        OSM ronse = osmService.getRonse();
        Map<Way, CoordinateLine> streetsOfRonse = osmUtils.getStreetsOfRonse(ronse);
        Map<CoordinateLine, Way> streetsByLine = streetsOfRonse.keySet().stream().collect(toMap(streetsOfRonse::get, identity()));
        Map<String, List<CoordinateLine>> streetsByName = streetsOfRonse.values().stream().collect(groupingBy(street -> ofNullable(streetsByLine.get(street).tags()).map(tags -> tags.get("name")).orElse("Unknown name")));
        List<StreetDto> streetDtosOfRonse = streetsByName.entrySet().stream().filter(entry -> entry.getValue().stream().flatMap(CoordinateLine::getCoordinateStream).count() > 1L).map(entry -> new StreetDto(entry.getKey(), entry.getValue())).collect(toList());
        model().bindViewToModel(streetDtosOfRonse);

        Map<String, List<Way>> collect = streetsOfRonse.keySet().stream().filter(way -> streetsOfRonse.get(way).getCoordinateStream().count() > 1L).collect(groupingBy(way -> ofNullable(way.tags().get("name")).orElse("Unknown name")));
        wayIdsByStreet = collect.entrySet().stream().collect(toMap(Map.Entry::getKey, entry -> entry.getValue().stream().map(Way::id).sorted().collect(toList())));

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
        System.out.println(view().streetOverviewTable().getSelectionModel().getSelectedItem().name().getValue() + ": " + wayIdsByStreet.get(view().streetOverviewTable().getSelectionModel().getSelectedItem().name().getValue()));
    }

    public void clearSelection() {
        model().clearSelection();
    }
}