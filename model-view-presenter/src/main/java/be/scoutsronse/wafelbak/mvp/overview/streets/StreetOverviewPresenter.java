package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.common.AccordionPane;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.way.WayService;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.tech.util.Collectors;
import com.sothawo.mapjfx.CoordinateLine;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
@DependsOn("clusterInitializer")
public class StreetOverviewPresenter extends Presenter<StreetOverviewModel, StreetOverviewView> {

    @Inject
    private MapPresenter mapPresenter;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private WayService wayService;

    @PostConstruct
    void initStreets() {
        Collection<Cluster> clusters = clusterRepository.findAll();
        Map<ClusterId, ClusterData> clusterData = clusters.stream().map(ClusterData::new).collect(Collectors.toMapByKey(ClusterData::id));
        Map<ClusterId, List<StreetData>> streetData = clusters.stream().map(Cluster::streets).flatMap(Collection::stream).map(street -> new StreetData(street, wayService.findCoordinateLinesBy(street.wayIds()))).collect(groupingBy(StreetData::clusterId));
        clusterData.forEach((key, value) -> value.setStreets(streetData.get(key)));
        model().bindViewToModel();
        model().setClusters(clusterData.values());
    }

    public void selectStreets(Collection<CoordinateLine> coordinateLines) {
        mapPresenter.selectStreets(coordinateLines);
    }

    public void clearSelection() {
        model().clearSelection();
    }

    public AccordionPane pane() {
        ChangeListener<TitledPane> expansionListener = null;
        ChangeListener<TitledPane> collapseListener = (observable, oldValue, newValue) -> clearSelection();
        return new AccordionPane(view().getPane(), expansionListener, collapseListener);
    }
}