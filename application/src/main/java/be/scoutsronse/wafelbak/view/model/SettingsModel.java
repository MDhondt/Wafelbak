package be.scoutsronse.wafelbak.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import static java.lang.Boolean.TRUE;
import static javafx.scene.paint.Color.*;

public class SettingsModel {

    private ObjectProperty<Color> streetOverviewColour = new SimpleObjectProperty<>(BLUE);
    private ObjectProperty<Color> saleOverviewNotStartedColour = new SimpleObjectProperty<>(RED);
    private ObjectProperty<Color> saleOverviewBusyColour = new SimpleObjectProperty<>(ORANGE);
    private ObjectProperty<Color> saleOverviewDoneColour = new SimpleObjectProperty<>(GREEN);
    private ObjectProperty<Color> borderColour = new SimpleObjectProperty<>(RED);
    private BooleanProperty borderVisibility = new SimpleBooleanProperty(TRUE);

    public Color getStreetOverviewColour() {
        return streetOverviewColour.get();
    }

    public ObjectProperty<Color> streetOverviewColourProperty() {
        return streetOverviewColour;
    }

    public Color getSaleOverviewNotStartedColour() {
        return saleOverviewNotStartedColour.get();
    }

    public ObjectProperty<Color> saleOverviewNotStartedColourProperty() {
        return saleOverviewNotStartedColour;
    }

    public Color getSaleOverviewBusyColour() {
        return saleOverviewBusyColour.get();
    }

    public ObjectProperty<Color> saleOverviewBusyColourProperty() {
        return saleOverviewBusyColour;
    }

    public Color getSaleOverviewDoneColour() {
        return saleOverviewDoneColour.get();
    }

    public ObjectProperty<Color> saleOverviewDoneColourProperty() {
        return saleOverviewDoneColour;
    }

    public Color getBorderColour() {
        return borderColour.get();
    }

    public ObjectProperty<Color> borderColourProperty() {
        return borderColour;
    }

    public boolean isBorderVisible() {
        return borderVisibility.get();
    }

    public BooleanProperty borderVisibilityProperty() {
        return borderVisibility;
    }
}