package be.scoutsronse.wafelbak.mvp.overview.sale;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.presenter.WafelbakPresenter;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.repository.ClusterStateRepository;
import be.scoutsronse.wafelbak.service.OpenedSaleService;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.tech.util.Collectors.toReversedList;
import static java.lang.System.getProperty;
import static java.time.LocalDate.now;

@Component
public class SaleOverviewPresenter extends Presenter<SaleOverviewModel, SaleOverviewView> {

    @Inject
    private WafelbakPresenter wafelbakPresenter;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private ClusterStateRepository clusterStateRepository;
    @Inject
    private OpenedSaleService openedSaleService;

    @Value("${db.path}")
    private String dbPath;

    @PostConstruct
    void init() {
        model().bindViewToModel();
    }

    public AccordionPane pane() {
        return new AccordionPane(view().getPane());
    }

    Stage mainStage() {
        return wafelbakPresenter.mainStage();
    }

    public boolean startNewSale() {
        List<Cluster> allClusters = clusterRepository.findAll();
        Integer year = now().getYear();
        boolean yearAlreadyExists = allClusters.stream().map(Cluster::states).flatMap(Collection::stream).map(ClusterState::year).anyMatch(year::equals);
        if (yearAlreadyExists) {
            if (view().overwriteExistingYear()) {
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
}