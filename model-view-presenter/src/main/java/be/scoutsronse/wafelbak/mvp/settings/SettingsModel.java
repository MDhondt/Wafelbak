package be.scoutsronse.wafelbak.mvp.settings;

import be.scoutsronse.wafelbak.mvp.Model;
import be.scoutsronse.wafelbak.mvp.i18n.i18n;
import com.sun.javafx.scene.control.skin.ColorPickerSkin;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.*;
import static javafx.scene.paint.Color.*;

public class SettingsModel extends Model<SettingsView> {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty colours = new SimpleStringProperty();

    public SettingsModel(SettingsView view) {
        super(view);
    }

    void bindViewToModel() {
        view().titleProperty().bind(title);
        view().coloursProperty().bind(colours);
        view().colourPickerStreetOverview().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_STREET_GENERAL));
        view().colourPickerSaleOverviewNotStarted().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_NOT_STARTED));
        view().colourPickerSaleOverviewBusy().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_BUSY));
        view().colourPickerSaleOverviewPartlyDone().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_PARTLY_DONE));
        view().colourPickerSaleOverviewPartlyDoneAndBusy().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_PARTLY_DONE_AND_BUSY));
        view().colourPickerSaleOverviewDone().skinProperty().addListener(getSkinChangeListener(COLOUR_PICKER_SALE_DONE));

        title.setValue(view().message(SETTINGS_TITLE));
        colours.setValue(view().message(SETTING_COLOURS));
    }

    private ChangeListener<Skin<?>> getSkinChangeListener(i18n text) {
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
    }
}