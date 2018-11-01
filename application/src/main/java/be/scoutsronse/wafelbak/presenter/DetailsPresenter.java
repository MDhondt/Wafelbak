package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.dto.ClusterDetail;
import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.domain.entity.Sale;
import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.repository.UndocumentedSaleRepository;
import be.scoutsronse.wafelbak.service.OpenedSaleService;
import be.scoutsronse.wafelbak.view.DetailsView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.tuple.Pair.of;

@Component
public class DetailsPresenter {

    @Inject
    private MessageSource messageSource;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private UndocumentedSaleRepository undocumentedSaleRepository;
    @Inject
    private OpenedSaleService openedSaleService;
    @Inject
    private MapPresenter mapPresenter;
    @Inject
    private SettingsPresenter settingsPresenter;

    private DetailsView view;

    void init() {
        view = new DetailsView(this, messageSource);
    }

    public AccordionPane pane() {
        ChangeListener<TitledPane> expansionListener = (observable, oldValue, newValue) -> view.populate(getDetails(), undocumentedSaleRepository.findByYear(openedSaleService.getCurrentYear().shortValue()).orElse(null));
        ChangeListener<TitledPane> collapseListener = (observable, oldValue, newValue) -> mapPresenter.selectStreets(emptyList());
        return new AccordionPane(view.getPane(), expansionListener, collapseListener);
    }

    public List<ClusterDetail> getDetails() {
        List<Cluster> clusters = clusterRepository.findAll();
        List<ClusterDetail> details = new LinkedList<>();

        for (Cluster cluster : clusters) {
            Optional<ClusterState> clusterState = cluster.states().stream().filter(state -> state.year().equals(openedSaleService.getCurrentYear())).findFirst();
            if (clusterState.isPresent()) {
                int sold = clusterState.get().sales().stream().filter(sale -> sale.money() != null && sale.money() > 0).mapToInt(Sale::amount).sum();
                double money = clusterState.get().sales().stream().map(Sale::money).filter(Objects::nonNull).mapToDouble(Float::doubleValue).sum();
                details.add(new ClusterDetail(cluster.id(), cluster.name(), sold, (float) money));
            }
        }

        return details.stream().sorted(comparing(ClusterDetail::getClusterName)).collect(toList());
    }

    public void select(ClusterId clusterId) {
        Optional<Cluster> cluster = clusterRepository.findById(clusterId.value());
        if (cluster.isPresent()) {
            Color colour = settingsPresenter.getStreetOverviewColour();
            mapPresenter.selectStreets(singletonList(of(cluster.get().streets().stream().map(Street::id).collect(toList()), colour)));
        }
    }
}