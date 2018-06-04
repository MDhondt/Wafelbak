package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.view.SettingsView;
import be.scoutsronse.wafelbak.view.component.AccordionPane;
import be.scoutsronse.wafelbak.view.model.SettingsModel;
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
}