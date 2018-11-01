package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.ClusterStatus;
import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.dto.dialog.EndSale;
import be.scoutsronse.wafelbak.domain.dto.dialog.StartSale;
import be.scoutsronse.wafelbak.domain.entity.*;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.*;
import be.scoutsronse.wafelbak.service.OpenedSaleService;
import be.scoutsronse.wafelbak.view.SaleOverviewView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

import static be.scoutsronse.wafelbak.domain.ClusterStatus.*;
import static be.scoutsronse.wafelbak.tech.util.Collectors.toReversedList;
import static be.scoutsronse.wafelbak.tech.util.PredicateUtils.not;
import static java.lang.System.getProperty;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.tuple.Pair.of;

@Component
public class SaleOverviewPresenter {

    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private ClusterStateRepository clusterStateRepository;
    @Inject
    private UndocumentedSaleRepository undocumentedSaleRepository;
    @Inject
    private SaleRepository saleRepository;
    @Inject
    private StreetRepository streetRepository;
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
        ChangeListener<TitledPane> expansionListener = null;
        ChangeListener<TitledPane> collapseListener = (observable, oldValue, newValue) -> view.clearSelection();
        return new AccordionPane(view.getPane(), expansionListener, collapseListener);
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
        UndocumentedSale undocumentedSale = new UndocumentedSale(year.shortValue());
        undocumentedSaleRepository.save(undocumentedSale);
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
        mapPresenter.selectStreets(findNotStartedStreets(streets));
    }

    public void selectBusyStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(findBusyStreets(streets));
    }

    public void selectDoneStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(findDoneStreets(streets));
    }

    public void selectPartlyDoneStreets(Collection<StreetId> streets) {
        mapPresenter.selectStreets(findPartlyDoneStreets(streets));
    }

    private Collection<Pair<? extends Collection<StreetId>, Color>> findNotStartedStreets(Collection<StreetId> streets) {
        return singletonList(of(streets, getNotStartedColour().get()));
    }

    private Collection<Pair<? extends Collection<StreetId>, Color>> findBusyStreets(Collection<StreetId> streets) {
        Cluster cluster = clusterRepository.findAll().stream()
                                           .filter(clstr -> clstr.streets().stream().map(Street::id).anyMatch(streets::contains))
                                           .findFirst().get();
        ClusterState state = cluster.states().stream()
                                    .filter(s -> s.year().equals(openedSaleService.getCurrentYear()))
                                    .findFirst().get();
        Pair<List<StreetId>, Color> done = Pair.of(state.streetsDone().stream().map(Street::id).filter(streets::contains).collect(toList()), getDoneColour().get());
        Pair<List<StreetId>, Color> notDone = Pair.of(cluster.streets().stream().map(Street::id).filter(not(done.getLeft()::contains)).filter(streets::contains).collect(toList()), getBusyColour().get());

        return asList(done, notDone);
    }

    private Collection<Pair<? extends Collection<StreetId>, Color>> findDoneStreets(Collection<StreetId> streets) {
        return singletonList(of(streets, getDoneColour().get()));
    }

    private Collection<Pair<? extends Collection<StreetId>, Color>> findPartlyDoneStreets(Collection<StreetId> streets) {
        Cluster cluster = clusterRepository.findAll().stream()
                                           .filter(clstr -> clstr.streets().stream().map(Street::id).anyMatch(streets::contains))
                                           .findFirst().get();
        ClusterState state = cluster.states().stream()
                                    .filter(s -> s.year().equals(openedSaleService.getCurrentYear()))
                                    .findFirst().get();
        Pair<List<StreetId>, Color> done = Pair.of(state.streetsDone().stream().map(Street::id).filter(streets::contains).collect(toList()), getDoneColour().get());
        Pair<List<StreetId>, Color> notDone = Pair.of(cluster.streets().stream().map(Street::id).filter(not(done.getLeft()::contains)).filter(streets::contains).collect(toList()), getNotStartedColour().get());

        return asList(done, notDone);
    }

    public void selectAll() {
        Map<Cluster, ClusterState> statesByCluster = clusterRepository.findAll().stream().collect(toMap(identity(), cluster -> cluster.states().stream().filter(s -> s.year().equals(openedSaleService.getCurrentYear())).findFirst().get()));
        Collection<Pair<? extends Collection<StreetId>, Color>> groups = new ArrayList<>();
        for (Map.Entry<Cluster, ClusterState> clusterAndState : statesByCluster.entrySet()) {
            if (clusterAndState.getValue().status().equals(NOT_STARTED)) {
                groups.addAll(findNotStartedStreets(clusterAndState.getKey().streets().stream().map(Street::id).collect(toList())));
            } else if (clusterAndState.getValue().status().equals(BUSY)) {
                groups.addAll(findBusyStreets(clusterAndState.getKey().streets().stream().map(Street::id).collect(toList())));
            } else if (clusterAndState.getValue().status().equals(PARTLY_DONE)) {
                groups.addAll(findPartlyDoneStreets(clusterAndState.getKey().streets().stream().map(Street::id).collect(toList())));
            } else {
                groups.addAll(findDoneStreets(clusterAndState.getKey().streets().stream().map(Street::id).collect(toList())));
            }
        }
        mapPresenter.selectStreets(groups);
    }

    public Cluster getClusterFor(ClusterId selectedClusterId) {
        return clusterRepository.findById(selectedClusterId.value()).get();
    }

    public void startSale(ClusterId clusterId, StartSale startSale) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        Collection<Street> streets = cluster.streets();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        List<Street> streetsToBeDone = streets.stream()
                                              .filter(street -> clusterState.streetsDone().stream()
                                                                            .map(Street::id)
                                                                            .noneMatch(street.id()::equals))
                                              .collect(toList());
        clusterState.setStatus(BUSY);
        Sale sale = new Sale(startSale.getAmount(),
                             startSale.getSalesMan(),
                             startSale.getContact(),
                             startSale.getStartTime(),
                             streetsToBeDone,
                             clusterState,
                             startSale.getSalesTeam());
        clusterState.addSale(sale);

        saleRepository.saveAndFlush(sale);
        clusterStateRepository.saveAndFlush(clusterState);
    }

    public void updateSale(ClusterId clusterId, StartSale updatedSale) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        Sale currentSale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        currentSale.setAmount(updatedSale.getAmount());
        currentSale.setSalesMan(updatedSale.getSalesMan());
        currentSale.setContact(updatedSale.getContact());
        currentSale.setStart(updatedSale.getStartTime());
        currentSale.setSalesTeam(updatedSale.getSalesTeam());

        saleRepository.saveAndFlush(currentSale);
    }

    public void updateSale(ClusterId clusterId, EndSale updatedSale) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        Sale currentSale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        currentSale.setAmount(updatedSale.getActualAmountSold());
        currentSale.setEnd(updatedSale.getEndTime());
        currentSale.setMoney(updatedSale.getMoney());

        saleRepository.saveAndFlush(currentSale);
    }

    public StartSale getBusySaleFor(ClusterId clusterId) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        Sale sale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        return new StartSale(sale.amount(), sale.salesMan(), sale.contact(), sale.salesTeam(), sale.start().toLocalTime());
    }

    public EndSale getEndSaleFor(ClusterId clusterId) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        Sale sale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        return new EndSale(sale.amount(), sale.money(), sale.end().toLocalTime(), sale.streets().stream().map(Street::id).collect(toList()));
    }

    public void undoStart(ClusterId id) {
        try {
            Cluster cluster = clusterRepository.findById(id.value()).get();
            Sale sale = null;
            try {
                ClusterState clusterState = cluster.states().stream()
                                                   .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                                   .findFirst().get();
                try {
                    sale = clusterState.sales().stream().sorted(comparing(Sale::start)).findFirst().get();
                    clusterState.removeSale(sale);
                } catch (Exception e) {
                }
                clusterState.setStatus(NOT_STARTED);
                clusterStateRepository.saveAndFlush(clusterState);
            } catch (Exception e) {
            }
            saleRepository.delete(sale);
            saleRepository.flush();
        } catch (Exception e) {}
    }

    public void endSale(ClusterId clusterId, EndSale endSale, ClusterStatus clusterStatus) {
        Cluster cluster = clusterRepository.findById(clusterId.value()).get();
        ClusterState clusterState = cluster.states().stream()
                                           .filter(state -> state.year().equals(openedSaleService.getCurrentYear()))
                                           .findFirst().get();
        Sale currentSale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        List<Street> doneStreets = endSale.getDoneStreets().stream().map(streetRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(toList());
        currentSale.setAmount(endSale.getActualAmountSold());
        currentSale.setMoney(endSale.getMoney());
        currentSale.setEnd(endSale.getEndTime());
        currentSale.setStreets(doneStreets);
        clusterState.setStatus(clusterStatus);
        doneStreets.forEach(clusterState::addStreetDone);

        saleRepository.save(currentSale);
        clusterStateRepository.saveAndFlush(clusterState);
    }
}