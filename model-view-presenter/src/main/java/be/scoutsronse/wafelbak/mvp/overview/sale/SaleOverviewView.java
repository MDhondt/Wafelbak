package be.scoutsronse.wafelbak.mvp.overview.sale;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class SaleOverviewView extends View<SaleOverviewPresenter> {

    private TitledPane pane;

    public SaleOverviewView(SaleOverviewPresenter presenter) {
        super(presenter);

        VBox vBox = new VBox();

        pane = new TitledPane();
        pane.setContent(vBox);
    }

    TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }
}