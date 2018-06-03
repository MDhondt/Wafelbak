package be.scoutsronse.wafelbak.view.model;

import be.scoutsronse.wafelbak.i18n.MessageTag;
import com.sun.javafx.scene.control.skin.ColorPickerSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.lang.Boolean.TRUE;
import static javafx.scene.paint.Color.*;

public class SettingsModel {

    private ObjectProperty<Color> streetOverviewColour = new SimpleObjectProperty<>(BLUE);
    private ObjectProperty<Color> saleOverviewNotStartedColour = new SimpleObjectProperty<>(RED);
    private ObjectProperty<Color> saleOverviewBusyColour = new SimpleObjectProperty<>(ORANGE);
    private ObjectProperty<Color> saleOverviewDoneColour = new SimpleObjectProperty<>(GREEN);
    private ObjectProperty<Color> borderColour = new SimpleObjectProperty<>(RED);
    private BooleanProperty borderVisibility = new SimpleBooleanProperty(TRUE);

    void bindViewToModel() {
        view().colourPickerStreetOverview().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_STREET_GENERAL));
        view().colourPickerSaleOverviewNotStarted().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_NOT_STARTED));
        view().colourPickerSaleOverviewBusy().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_BUSY));
        view().colourPickerSaleOverviewDone().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_DONE));
        view().colourPickerBorder().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_BORDER));
    }

    private ChangeListener<Skin<?>> getSkinChangeListener(MessageTag text) {
        return (observable, oldSkin, newSkin) -> {
            ((Label) ((ColorPickerSkin) newSkin).getDisplayNode()).textProperty().addListener(
                    (observable1, oldValue1, newValue1) -> ((Label) ((ColorPickerSkin) newSkin).getDisplayNode()).textProperty().setValue(SettingsModel.this.view().message(text)));
        };
    }

    void initSelectionColours() {
        view().colourPickerStreetOverview().setValue(BLUE);
        view().colourPickerSaleOverviewNotStarted().setValue(YELLOW);
        view().colourPickerSaleOverviewBusy().setValue(ORANGE);
        view().colourPickerSaleOverviewPartlyDone().setValue(BLACK);
        view().colourPickerSaleOverviewPartlyDoneAndBusy().setValue(PURPLE);
        view().colourPickerSaleOverviewDone().setValue(GREEN);
        view().colourPickerBorder().setValue(RED);
    }

    Color streetOverviewColour() {
        return view().colourPickerStreetOverview().getValue();
    }

    Color saleOverviewNotStarted() {
        return view().colourPickerSaleOverviewNotStarted().getValue();
    }

    Color saleOverviewBusy() {
        return view().colourPickerSaleOverviewBusy().getValue();
    }

    Color saleOverviewPartlyDone() {
        return view().colourPickerSaleOverviewPartlyDone().getValue();
    }

    Color saleOverviewPartlyDoneAndBusy() {
        return view().colourPickerSaleOverviewPartlyDoneAndBusy().getValue();
    }

    Color saleOverviewDone() {
        return view().colourPickerSaleOverviewDone().getValue();
    }
}