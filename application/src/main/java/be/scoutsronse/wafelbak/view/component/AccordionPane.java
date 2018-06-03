package be.scoutsronse.wafelbak.view.component;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TitledPane;

public class AccordionPane {

    private TitledPane titledPane;
    private ChangeListener<TitledPane> expansionListener;
    private ChangeListener<TitledPane> collapseListener;

    public AccordionPane(TitledPane pane) {
        this(pane, null, null);
    }

    public AccordionPane(TitledPane titledPane, ChangeListener<TitledPane> expansionListener, ChangeListener<TitledPane> collapseListener) {
        this.titledPane = titledPane;
        this.expansionListener = expansionListener;
        this.collapseListener = collapseListener;
    }

    public TitledPane pane() {
        return titledPane;
    }

    public ChangeListener<TitledPane> collapseListener() {
        if (collapseListener != null) {
            return collapseListener;
        }
        return doNothing;
    }

    public ChangeListener<TitledPane> expansionListener() {
        if (expansionListener != null) {
            return expansionListener;
        }
        return doNothing;
    }

    private static ChangeListener<TitledPane> doNothing = (observable, oldValue, newValue) -> {};
}