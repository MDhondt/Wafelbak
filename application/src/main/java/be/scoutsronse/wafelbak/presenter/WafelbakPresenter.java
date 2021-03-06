package be.scoutsronse.wafelbak.presenter;

import be.scoutsronse.wafelbak.view.WafelbakView;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javax.inject.Inject;


@Component
public class WafelbakPresenter {

    @Inject
    private StreetOverviewPresenter streetOverviewPresenter;
    @Inject
    private SaleOverviewPresenter saleOverviewPresenter;
    @Inject
    private DetailsPresenter detailsPresenter;
    @Inject
    private SettingsPresenter settingsPresenter;
    @Inject
    private MapPresenter mapPresenter;

    private final WafelbakView view;

    private WafelbakPresenter() {
        this.view = new WafelbakView();
    }

    public void show(Stage mainStage) {
        view.setStage(mainStage);

        mapPresenter.init();
        streetOverviewPresenter.init();
        saleOverviewPresenter.init(mainStage);
        detailsPresenter.init(mainStage);
        settingsPresenter.init();

        view.addLeftTools(streetOverviewPresenter.pane(),
                          saleOverviewPresenter.pane(),
                          detailsPresenter.pane(),
                          settingsPresenter.pane());
        view.addMap(mapPresenter.getMap());

        view.show();
    }
}