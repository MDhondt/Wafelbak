package be.scoutsronse.wafelbak.mvp.settings;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.Locale;

import static org.controlsfx.tools.Borders.wrap;

public class SettingsView extends View<SettingsPresenter> {

    private TitledPane pane;
    private Label colours;
    private ColorPicker streetOverviewColour;
    private ColorPicker saleOverviewBusyColour;
    private ColorPicker saleOverviewNotStartedColour;
    private ColorPicker saleOverviewDoneColour;
    private ColorPicker saleOverviewPartlyDoneColour;
    private ColorPicker saleOverviewPartlyDoneAndBusyColour;

    public SettingsView(SettingsPresenter presenter) {
        super(presenter);
        Locale.setDefault(View.locale);

        VBox vBox = new VBox(10);
        colours = new Label();

        streetOverviewColour = new ColorPicker();
        streetOverviewColour.prefWidthProperty().bind(vBox.widthProperty());
        streetOverviewColour.setMaxWidth(200);
        saleOverviewBusyColour = new ColorPicker();
        saleOverviewBusyColour.prefWidthProperty().bind(vBox.widthProperty());
        saleOverviewBusyColour.setMaxWidth(200);
        saleOverviewNotStartedColour = new ColorPicker();
        saleOverviewNotStartedColour.prefWidthProperty().bind(vBox.widthProperty());
        saleOverviewNotStartedColour.setMaxWidth(200);
        saleOverviewDoneColour = new ColorPicker();
        saleOverviewDoneColour.prefWidthProperty().bind(vBox.widthProperty());
        saleOverviewDoneColour.setMaxWidth(200);
        saleOverviewPartlyDoneColour = new ColorPicker();
        saleOverviewPartlyDoneColour.prefWidthProperty().bind(vBox.widthProperty());
        saleOverviewPartlyDoneColour.setMaxWidth(200);
        saleOverviewPartlyDoneAndBusyColour = new ColorPicker();
        saleOverviewPartlyDoneAndBusyColour.prefWidthProperty().bind(vBox.widthProperty());
        saleOverviewPartlyDoneAndBusyColour.setMaxWidth(200);

        Node streetOverviewBox = wrap(streetOverviewColour).lineBorder().title("Overzicht verkoop").buildAll();
        VBox saleOverviewGroup = new VBox(5);
        saleOverviewGroup.getChildren().addAll(saleOverviewNotStartedColour, saleOverviewBusyColour, saleOverviewPartlyDoneColour, saleOverviewPartlyDoneAndBusyColour, saleOverviewDoneColour);
        Node saleOverviewBox = wrap(saleOverviewGroup).lineBorder().title("Overzicht straten").buildAll();
        vBox.getChildren().addAll(colours, streetOverviewBox, saleOverviewBox);

        pane = new TitledPane();
        pane.setContent(vBox);
    }

    TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }

    StringProperty coloursProperty() {
        return colours.textProperty();
    }

    ColorPicker colourPickerStreetOverview() {
        return streetOverviewColour;
    }

    ColorPicker colourPickerSaleOverviewNotStarted() {
        return saleOverviewNotStartedColour;
    }

    ColorPicker colourPickerSaleOverviewBusy() {
        return saleOverviewBusyColour;
    }

    ColorPicker colourPickerSaleOverviewPartlyDone() {
        return saleOverviewPartlyDoneColour;
    }

    ColorPicker colourPickerSaleOverviewPartlyDoneAndBusy() {
        return saleOverviewPartlyDoneAndBusyColour;
    }

    ColorPicker colourPickerSaleOverviewDone() {
        return saleOverviewDoneColour;
    }
}