package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.ClusterStatus;
import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.repository.ClusterStateRepository;
import be.scoutsronse.wafelbak.service.OpenedSaleService;
import be.scoutsronse.wafelbak.view.SaleOverviewView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toReversedList;
import static be.scoutsronse.wafelbak.tech.util.PredicateUtils.not;
import static java.lang.System.getProperty;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.tuple.Pair.of;

@Component
public class SaleOverviewPresenter {

    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private ClusterStateRepository clusterStateRepository;
    @Inject
    private OpenedSaleService openedSaleService;
    @Inject
    private MessageSource messageSource;
    @Inject
    private SettingsPresenter settingsPresenter;
    @Inject
    private MapPresenter mapPresenter;

    @Value("${db.path}")
    private String dbPath;

    private SaleOverviewView view;

    void init(Stage mainStage) {
        view = new SaleOverviewView(this, messageSource, mainStage);
    }

    public AccordionPane pane() {
        return new AccordionPane(view.getPane());
    }

    public boolean startNewSale() {
        List<Cluster> allClusters = clusterRepository.findAll();
        Integer year = now().getYear();
        boolean yearAlreadyExists = allClusters.stream().map(Cluster::states).flatMap(Collection::stream).map(ClusterState::year).anyMatch(year::equals);
        if (yearAlreadyExists) {
            if (view.confirmOverwriteExistingYear()) {
                allClusters.forEach(cluster -> {
                    Optional<ClusterState> state = cluster.removeState(year);
                    state.ifPresent(s -> clusterStateRepository.delete(s));
                });
                clusterRepository.saveAll(allClusters);
            } else {
                return false;
            }
        }
        allClusters.forEach(cluster -> {
            ClusterState newState = cluster.addNewState(year);
            clusterStateRepository.save(newState);
        });
        clusterRepository.saveAll(allClusters);
        openedSaleService.setCurrentYear(year);
        return true;
    }

    public List<Integer> existingSales() {
        return clusterRepository.findAll().stream()
                                .map(Cluster::states).flatMap(Collection::stream)
                                .map(ClusterState::year)
                                .distinct()
                                .sorted()
                                .collect(toReversedList());
    }

    public String dbPath() {
        return dbPath.replaceFirst("^~", getProperty("user.home").replace("\\", "\\\\")).replace("/", "\\") + ".mv.db";
    }

    public void setCurrentSale(Integer year) {
        openedSaleService.setCurrentYear(year);
    }

    public Collection<ClusterDto> getClustersFor(ClusterStatus status) {
        return clusterRepository.findAll().stream()
                                .filter(cluster -> cluster.states().stream()
                                                          .anyMatch(state -> state.year().equals(openedSaleService.getCurrentYear()) && state.status().equals(status)))
                                .map(ClusterDto::new)
                                .collect(toList());
    }

    public ObjectProperty<Color> getNotStartedColour() {
        return settingsPresenter.getNotStartedColourProperty();
    }

    public ObjectProperty<Color> getBusyColour() {
        return settingsPresenter.getSaleOverviewBusyColourProperty();
    }

    public ObjectProperty<Color> getDoneColour() {
        return settingsPresenter.getSaleOverviewDoneColourProperty();
    }

    public void selectNotStartedStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(singletonList(of(streets, getNotStartedColour().get())));
    }

    public void selectBusyStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(singletonList(of(streets, getBusyColour().get())));
    }

    public void selectDoneStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(singletonList(of(streets, getDoneColour().get())));
    }

    public void selectPartlyDoneStreets(Collection<StreetId> streets) {
        Cluster cluster = clusterRepository.findAll().stream()
                                           .filter(clstr -> clstr.streets().stream().map(Street::id).anyMatch(streets::contains))
                                           .findFirst().get();
        ClusterState state = cluster.states().stream()
                                    .filter(s -> s.year().equals(openedSaleService.getCurrentYear()))
                                    .findFirst().get();
        Pair<List<StreetId>, Color> done = Pair.of(state.streetsDone().stream().map(Street::id).collect(toList()), getDoneColour().get());
        Pair<List<StreetId>, Color> notDone = Pair.of(cluster.streets().stream().map(Street::id).filter(not(done.getLeft()::contains)).collect(toList()), getNotStartedColour().get());

        mapPresenter.selectStreets(asList(done, notDone));
    }
}