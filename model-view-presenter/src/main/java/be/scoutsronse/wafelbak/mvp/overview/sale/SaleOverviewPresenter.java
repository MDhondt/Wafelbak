package be.scoutsronse.wafelbak.mvp.overview.sale;

import be.scoutsronse.wafelbak.mvp.Presenter;
import be.scoutsronse.wafelbak.mvp.common.AccordionPane;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SaleOverviewPresenter extends Presenter<SaleOverviewModel, SaleOverviewView> {

    @PostConstruct
    void init() {
        model().bindViewToModel();
    }

    public AccordionPane pane() {
        return new AccordionPane(view().getPane());
    }
}