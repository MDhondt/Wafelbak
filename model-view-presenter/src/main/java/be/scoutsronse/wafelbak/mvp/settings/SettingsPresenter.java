package be.scoutsronse.wafelbak.mvp.settings;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.common.AccordionPane;
import be.scoutsronse.wafelbak.tech.event.ApplicationStarted;
import be.scoutsronse.wafelbak.tech.event.eventbus.Subscribe;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SettingsPresenter extends Presenter<SettingsModel, SettingsView> {

    @PostConstruct
    void init() {
        model().bindViewToModel();
    }

    public AccordionPane pane() {
        return new AccordionPane(view().getPane());
    }

    @Subscribe
    public void initSelectionColours(ApplicationStarted event) {
        model().initSelectionColours();
    }
}