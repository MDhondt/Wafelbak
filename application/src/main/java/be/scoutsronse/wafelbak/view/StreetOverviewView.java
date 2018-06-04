package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.presenter.StreetOverviewPresenter;
import be.scoutsronse.wafelbak.view.component.treeview.SearchableClusterTreeView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.springframework.context.MessageSource;

import java.util.Collection;

import static be.scoutsronse.wafelbak.i18n.MessageTag.STREET_OVERVIEW_TITLE;
import static javafx.geometry.Pos.TOP_CENTER;

public class StreetOverviewView extends AbstractView {

    private TitledPane pane;
    private SearchableClusterTreeView clusterTreeView;

    public StreetOverviewView(StreetOverviewPresenter presenter, MessageSource messageSource) {
        super(messageSource);

        clusterTreeView = new SearchableClusterTreeView(presenter::selectStreets);
        VBox content = new VBox();
        content.setAlignment(TOP_CENTER);
        content.getChildren().add(clusterTreeView);
        clusterTreeView.prefWidthProperty().bind(content.widthProperty());
        clusterTreeView.maxWidthProperty().bind(content.widthProperty());
        clusterTreeView.prefHeightProperty().bind(content.heightProperty());
        clusterTreeView.maxHeightProperty().bind(content.heightProperty());

        pane = new TitledPane(message(STREET_OVERVIEW_TITLE), content);
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