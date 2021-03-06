package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.i18n.MessageTag;
import be.scoutsronse.wafelbak.model.SettingsModel;
import be.scoutsronse.wafelbak.presenter.SettingsPresenter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.context.MessageSource;

import java.util.List;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.util.Arrays.asList;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontWeight.BOLD;
import static org.controlsfx.tools.Borders.wrap;

public class SettingsView extends AbstractView {

    private SettingsPresenter presenter;
    private TitledPane pane;

    public SettingsView(SettingsPresenter presenter, SettingsModel model, MessageSource messageSource) {
        super(messageSource);
        this.presenter = presenter;

        VBox empty = new VBox();
        empty.setMinHeight(20);

        VBox content = new VBox(5);
        content.getChildren().addAll(buildStreetColourSelectionMenu(model, content.widthProperty()));
        content.getChildren().add(empty);
        content.getChildren().addAll(buildBorderSelectionMenu(model, content.widthProperty()));

        pane = new TitledPane(message(SETTINGS_TITLE), content);
    }

    public TitledPane getPane() {
        return pane;
    }

    private List<Node> buildStreetColourSelectionMenu(SettingsModel model, ReadOnlyDoubleProperty menuWidth) {
        Label colours = new Label(message(SETTING_COLOURS));
        colours.setFont(font(colours.getFont().getFamily(), BOLD, colours.getFont().getSize()));

        ColorPicker streetOverviewColour = createColourPicker(COLOUR_PICKER_STREET_GENERAL, model.streetOverviewColourProperty(), menuWidth);
        VBox streetOverviewBox = new VBox(5);
        streetOverviewBox.getChildren().addAll(streetOverviewColour);
        Node streetOverviewBorderedBox = wrap(streetOverviewBox).lineBorder().title(message(STREET_OVERVIEW_TITLE)).buildAll();

        ColorPicker saleOverviewNotStartedColour = createColourPicker(COLOUR_PICKER_SALE_NOT_STARTED, model.saleOverviewNotStartedColourProperty(), menuWidth);
        ColorPicker saleOverviewBusyColour = createColourPicker(COLOUR_PICKER_SALE_BUSY, model.saleOverviewBusyColourProperty(), menuWidth);
        ColorPicker saleOverviewDoneColour = createColourPicker(COLOUR_PICKER_SALE_DONE, model.saleOverviewDoneColourProperty(), menuWidth);
        VBox saleOverviewBox = new VBox(5);
        saleOverviewBox.getChildren().addAll(saleOverviewNotStartedColour, saleOverviewBusyColour, saleOverviewDoneColour);
        Node saleOverviewBorderedBox = wrap(saleOverviewBox).lineBorder().title(message(SALE_OVERVIEW_TITLE)).buildAll();

        return asList(colours, streetOverviewBorderedBox, saleOverviewBorderedBox);
    }

    private List<Node> buildBorderSelectionMenu(SettingsModel model, ReadOnlyDoubleProperty menuWidth) {
        Label border = new Label(message(SETTING_BORDER));
        border.setFont(font(border.getFont().getFamily(), BOLD, border.getFont().getSize()));

        ColorPicker borderColour = createColourPicker(COLOUR_PICKER_BORDER, model.borderColourProperty(), menuWidth);
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.selectedProperty().bindBidirectional(model.borderVisibilityProperty());
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue) {
                toggleSwitch.setText(message(ON));
            } else {
                toggleSwitch.setText(message(OFF));
            }
            changeBorder();
        });
        borderColour.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                changeBorder();
            }
        });
        VBox borderBox = new VBox(5);
        borderBox.getChildren().addAll(borderColour, new HBox(toggleSwitch));
        Node borderBorderedBox = wrap(borderBox).lineBorder().buildAll();

        return asList(border, borderBorderedBox);
    }

    private ColorPicker createColourPicker(MessageTag text, ObjectProperty<Color> colourProperty, ReadOnlyDoubleProperty menuWidth) {
        ColorPicker colourPicker = new ColorPicker();
        colourPicker.prefWidthProperty().bind(menuWidth);
        colourPicker.setMaxWidth(225);
        colourPicker.skinProperty().addListener((observable, oldSkin, newSkin) -> {
            ((Label) ((ColorPickerSkin) newSkin).getDisplayNode()).textProperty().addListener(
                    (observable1, oldValue1, newValue1) -> ((Label) ((ColorPickerSkin) newSkin).getDisplayNode()).textProperty().setValue(message(text)));
        });
        colourPicker.valueProperty().bindBidirectional(colourProperty);
        return colourPicker;
    }

    private void changeBorder() {
        presenter.changeBorder();
    }
}