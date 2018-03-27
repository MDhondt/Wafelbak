package be.scoutsronse.wafelbak.mvp.overview.sale;

import be.scoutsronse.wafelbak.mvp.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.SALE_OVERVIEW_TITLE;

public class SaleOverviewModel extends Model<SaleOverviewView> {

    private StringProperty title = new SimpleStringProperty();

    public SaleOverviewModel(SaleOverviewView view) {
        super(view);
    }

    void bindViewToModel() {
        view().titleProperty().bind(title);
        title.setValue(view().message(SALE_OVERVIEW_TITLE));
    }
}