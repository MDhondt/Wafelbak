package be.scoutsronse.wafelbak.mvp.main;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.map.MapPresenter;
import be.scoutsronse.wafelbak.mvp.overview.streets.StreetOverviewPresenter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@Component
public class WafelbakPresenter extends Presenter<WafelbakModel, WafelbakView> {

    @Inject
    private StreetOverviewPresenter streetOverviewPresenter;
    @Inject
    private MapPresenter mapPresenter;

    @PostConstruct
    void initView() {
        view().addLeftTools(streetOverviewPresenter.pane());
        view().addMap(mapPresenter.view().getMap());
    }
}