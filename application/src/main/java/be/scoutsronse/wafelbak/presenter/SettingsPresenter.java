package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.model.SettingsModel;
import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import be.scoutsronse.wafelbak.tech.event.eventbus.Subscribe;
import be.scoutsronse.wafelbak.tech.util.FXUtils;
import be.scoutsronse.wafelbak.view.SettingsView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SettingsPresenter {

    @Inject
    private MapPresenter mapPresenter;
    @Inject
    private MessageSource messageSource;

    private SettingsModel model;
    private SettingsView view;

    void init() {
        model = new SettingsModel();
        view = new SettingsView(this, model, messageSource);
    }

    AccordionPane pane() {
        return new AccordionPane(view.getPane());
    }

    public void changeBorder() {
        mapPresenter.changeBorder(model.getBorderColour(), model.isBorderVisible());
    }

    public Color getStreetOverviewColour() {
        return model.getStreetOverviewColour();
    }

    public ObjectProperty<Color> getNotStartedColourProperty() {
        return model.saleOverviewNotStartedColourProperty();
    }

    public ObjectProperty<Color> getSaleOverviewBusyColourProperty() {
        return model.saleOverviewBusyColourProperty();
    }

    public ObjectProperty<Color> getSaleOverviewDoneColourProperty() {
        return model.saleOverviewDoneColourProperty();
    }

    @Subscribe
    public void initializeColourPickers(ApplicationStarted event) {
        FXUtils.executeOnFXThread(() -> model.setDefaultValues());
    }
}