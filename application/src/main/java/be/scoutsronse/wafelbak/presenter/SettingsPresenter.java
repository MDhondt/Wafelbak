package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import be.scoutsronse.wafelbak.tech.event.eventbus.Subscribe;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static be.scoutsronse.wafelbak.tech.util.FXUtils.executeOnFXThread;

@Component
public class SettingsPresenter {

    @Inject
    private MapPresenter mapPresenter;

    void init() {
        model().bindViewToModel();
    }

    public AccordionPane pane() {
        return new AccordionPane(view().getPane());
    }

    @Subscribe
    public void initSelectionColours(ApplicationStarted event) {
        executeOnFXThread(() -> model().initSelectionColours());
    }

    public Color streetOverviewColour() {
        return model().streetOverviewColour();
    }

    public Color saleOverviewNotStarted() {
        return model().saleOverviewNotStarted();
    }

    public Color saleOverviewBusy() {
        return model().saleOverviewBusy();
    }

    public Color saleOverviewPartlyDone() {
        return model().saleOverviewPartlyDone();
    }

    public Color saleOverviewPartlyDoneAndBusy() {
        return model().saleOverviewPartlyDoneAndBusy();
    }

    public Color saleOverviewDone() {
        return model().saleOverviewDone();
    }

    public void changeBorder(Color color, boolean visible) {
        mapPresenter.changeBorder(color, visible);
    }
}