package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.dto.ClusterDetail;
import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.domain.entity.Sale;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.view.DetailsView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
public class DetailsPresenter {

    @Inject
    private MessageSource messageSource;
    @Inject
    private ClusterRepository clusterRepository;

    private DetailsView view;

    void init() {
        view = new DetailsView(this, messageSource);
    }

    public AccordionPane pane() {
        return new AccordionPane(view.getPane());
    }

    public List<ClusterDetail> getDetails() {
        List<Cluster> clusters = clusterRepository.findAll();
        List<ClusterDetail> details = new LinkedList<>();

        for (Cluster cluster : clusters) {
            ClusterState clusterState = cluster.states().stream().sorted(comparing(ClusterState::year).reversed()).findFirst().get();
            int sold = clusterState.sales().stream().filter(sale -> sale.money() != null && sale.money() > 0).mapToInt(Sale::amount).sum();
            double money = clusterState.sales().stream().map(Sale::money).filter(Objects::nonNull).mapToDouble(Float::doubleValue).sum();
            details.add(new ClusterDetail(cluster.id(), cluster.name(), sold, (float) money));
        }

        return details.stream().sorted(comparing(ClusterDetail::getClusterName)).collect(toList());
    }
}