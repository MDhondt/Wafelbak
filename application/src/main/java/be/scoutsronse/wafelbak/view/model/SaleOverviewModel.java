package be.scoutsronse.wafelbak.view.model;

import be.scoutsronse.wafelbak.view.SaleOverviewView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static be.scoutsronse.wafelbak.i18n.MessageTag.SALE_OVERVIEW_TITLE;

public class SaleOverviewModel {

    private StringProperty title = new SimpleStringProperty();

    public SaleOverviewModel(SaleOverviewView view) {
        super(view);
    }

    void bindViewToModel() {
        view().titleProperty().bind(title);
        title.setValue(view().message(SALE_OVERVIEW_TITLE));
    }
}