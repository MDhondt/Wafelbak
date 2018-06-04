package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.presenter.StreetOverviewPresenter;
import be.scoutsronse.wafelbak.view.component.treeview.SearchableClusterTreeView;
import javafx.scene.control.TitledPane;
import org.springframework.context.MessageSource;

import java.util.Collection;

import static be.scoutsronse.wafelbak.i18n.MessageTag.STREET_OVERVIEW_TITLE;

public class StreetOverviewView extends AbstractView {

    private TitledPane pane;
    private SearchableClusterTreeView clusterTreeView;

    public StreetOverviewView(StreetOverviewPresenter presenter, MessageSource messageSource) {
        super(messageSource);

        clusterTreeView = new SearchableClusterTreeView(presenter::selectStreets);

        pane = new TitledPane(message(STREET_OVERVIEW_TITLE), clusterTreeView);
    }

    public TitledPane getPane() {
        return pane;
    }

    public void clearSelection() {
        clusterTreeView.clearSelection();
    }

    public void setClusters(Collection<ClusterDto> clusters) {
        clusterTreeView.setContent(clusters);
    }
}