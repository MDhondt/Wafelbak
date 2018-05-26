package be.scoutsronse.wafelbak.mvp.main;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.overview.sale.SaleOverviewPresenter;
import be.scoutsronse.wafelbak.mvp.overview.streets.StreetOverviewPresenter;
import be.scoutsronse.wafelbak.mvp.settings.SettingsPresenter;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@Component
public class WafelbakPresenter extends Presenter<WafelbakModel, WafelbakView> {

    @Inject
    private StreetOverviewPresenter streetOverviewPresenter;
    @Inject
    private SaleOverviewPresenter saleOverviewPresenter;
    @Inject
    private SettingsPresenter settingsPresenter;
    @Inject
    private MapPresenter mapPresenter;

    @PostConstruct
    void initView() {
        view().addLeftTools(streetOverviewPresenter.pane(),
                saleOverviewPresenter.pane(),
                settingsPresenter.pane());
        view().addMap(mapPresenter.view().getMap());
    }

    public Stage mainStage() {
        return view().mainStage();
    }
}