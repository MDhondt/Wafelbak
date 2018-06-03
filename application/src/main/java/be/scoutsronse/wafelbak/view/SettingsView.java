package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.presenter.SettingsPresenter;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.ToggleSwitch;

import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontWeight.BOLD;
import static org.controlsfx.tools.Borders.wrap;

public class SettingsView {

    private TitledPane pane;
    private Label colours;
    private Label border;
    private ColorPicker streetOverviewColour;
    private ColorPicker saleOverviewBusyColour;
    private ColorPicker saleOverviewNotStartedColour;
    private ColorPicker saleOverviewDoneColour;
    private ColorPicker borderColour;
    private ToggleSwitch toggleSwitch;

    public SettingsView(SettingsPresenter presenter) {

        VBox vBox = new VBox(5);
        colours = new Label();
        colours.setFont(font(colours.getFont().getFamily(), BOLD, colours.getFont().getSize()));
        border = new Label();
        border.setFont(font(border.getFont().getFamily(), BOLD, border.getFont().getSize()));
        toggleSwitch = new ToggleSwitch();
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue) {
                toggleSwitch.setText("Aan");
            } else {
                toggleSwitch.setText("Uit");
            }
            changeBorder(borderColour.getValue(), newValue != null && newValue);
        });

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
        borderColour = new ColorPicker();
        borderColour.prefWidthProperty().bind(vBox.widthProperty());
        borderColour.setMaxWidth(200);
        toggleSwitch.setSelected(true);
        borderColour.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                changeBorder(newValue, toggleSwitch.isSelected());
            }
        });

        Node streetOverviewBox = wrap(streetOverviewColour).lineBorder().title("Overzicht verkoop").buildAll();
        VBox saleOverviewGroup = new VBox(5);
        saleOverviewGroup.getChildren().addAll(saleOverviewNotStartedColour, saleOverviewBusyColour, saleOverviewPartlyDoneColour, saleOverviewPartlyDoneAndBusyColour, saleOverviewDoneColour);
        Node saleOverviewBox = wrap(saleOverviewGroup).lineBorder().title("Overzicht straten").buildAll();
        VBox borderGroup = new VBox(5);
        borderGroup.getChildren().addAll(borderColour, new HBox(toggleSwitch));
        Node borderBox = wrap(borderGroup).lineBorder().buildAll();
        VBox empty = new VBox();
        empty.setMinHeight(20);
        vBox.getChildren().addAll(
                colours,
                streetOverviewBox,
                saleOverviewBox,
                empty,
                border,
                borderBox);

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

    StringProperty borderProperty() {
        return border.textProperty();
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

    ColorPicker colourPickerBorder() {
        return borderColour;
    }

    private void changeBorder(Color color, boolean visible) {
        presenter().changeBorder(color, visible);
    }
}