package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.id.StreetId;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import be.scoutsronse.wafelbak.view.StreetOverviewView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Component
@DependsOn("clusterInitializer")
public class StreetOverviewPresenter {

    @Inject
    private MessageSource messageSource;
    @Inject
    private ClusterRepository clusterRepository;
    @Inject
    private SettingsPresenter settingsPresenter;
    @Inject
    private MapPresenter mapPresenter;

    private StreetOverviewView view;

    void init() {
        view = new StreetOverviewView(this, messageSource);
        view.setClusters(clusterRepository.findAll().stream().map(ClusterDto::new).collect(toList()));
    }

    AccordionPane pane() {
        ChangeListener<TitledPane> expansionListener = null;
        ChangeListener<TitledPane> collapseListener = (observable, oldValue, newValue) -> view.clearSelection();
        return new AccordionPane(view.getPane(), expansionListener, collapseListener);
    }

    public void selectStreets(Collection<StreetId> streets) {
        Color colour = settingsPresenter.getStreetOverviewColour();
        mapPresenter.selectStreets(streets, colour);
    }
}