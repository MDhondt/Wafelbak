package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.common.AccordionPane;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.settings.SettingsPresenter;
import be.scoutsronse.wafelbak.mvp.way.WayService;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toMapByKey;
import static java.util.stream.Collectors.groupingBy;

@Component
@DependsOn("clusterInitializer")
public class StreetOverviewPresenter extends Presenter<StreetOverviewModel, StreetOverviewView> {

    @Inject
    private MapPresenter mapPresenter;
    @Inject
    private SettingsPresenter settingsPresenter;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private WayService wayService;

    @PostConstruct
    void initStreets() {
        Collection<Cluster> clusters = clusterRepository.findAll();
        Map<ClusterId, ClusterData> clusterData = clusters.stream().map(ClusterData::new).collect(toMapByKey(ClusterData::id));
        Map<ClusterId, List<StreetData>> streetData = clusters.stream().map(Cluster::streets).flatMap(Collection::stream).map(street -> new StreetData(street, wayService.findCoordinateLinesBy(street.wayIds()))).collect(groupingBy(StreetData::clusterId));
        clusterData.forEach((key, value) -> value.setStreets(streetData.get(key)));
        model().bindViewToModel();
        model().setAllClusters(clusterData.values());
    }

    void selectStreets(Collection<CoordinateLine> coordinateLines) {
        Color colour = settingsPresenter.streetOverviewColour();
        mapPresenter.selectStreets(coordinateLines, colour);
    }

    public void clearSelection() {
        model().clearSelection();
    }

    public AccordionPane pane() {
        ChangeListener<TitledPane> expansionListener = null;
        ChangeListener<TitledPane> collapseListener = (observable, oldValue, newValue) -> clearSelection();
        return new AccordionPane(view().getPane(), expansionListener, collapseListener);
    }

    public void search(String searchInput) {
        Collection<ClusterData> currentClusters = model().getAllClusters();
        Collection<ClusterData> newClusters = new ArrayList<>();
        for (ClusterData cluster : currentClusters) {
            if (cluster.toString().toLowerCase().contains(searchInput.toLowerCase())) {
                newClusters.add(cluster);
            } else if (cluster.streets().stream().map(StreetData::toString).map(String::toLowerCase).anyMatch(street -> street.contains(searchInput.toLowerCase()))) {
                newClusters.add(cluster);
            }
        }
        model().setClusters(newClusters, searchInput);
    }
}