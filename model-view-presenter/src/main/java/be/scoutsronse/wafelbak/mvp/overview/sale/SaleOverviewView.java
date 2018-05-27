package be.scoutsronse.wafelbak.mvp.overview.sale;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.mvp.i18n.i18n.*;
import static java.time.LocalDate.now;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.ButtonBar.ButtonData.OTHER;
import static javafx.stage.Modality.WINDOW_MODAL;

public class SaleOverviewView extends View<SaleOverviewPresenter> {

    private TitledPane pane;
    private VBox unopenedSale;
    private VBox openedSale;

    public SaleOverviewView(SaleOverviewPresenter presenter) {
        super(presenter);

        unopenedSale = createUnopenedSale();
        openedSale = new VBox();

        pane = new TitledPane();
        pane.setContent(unopenedSale);
    }

    private VBox createUnopenedSale() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(TOP_CENTER);
        Button open = new Button("Open bestaande verkoop");
        Button start = new Button("Start nieuwe verkoop");
        VBox empty = new VBox();
        empty.setPrefHeight(20);
        vBox.getChildren().addAll(empty, open, start);
        open.prefWidthProperty().bind(vBox.widthProperty().subtract(100));
        start.prefWidthProperty().bind(vBox.widthProperty().subtract(100));

        open.setOnAction(event -> {
            List<Integer> existingSales = presenter().existingSales();
            if (existingSales.isEmpty()) {
                Alert dialog = new Alert(INFORMATION);
                dialog.setTitle(message(NO_EXISTING_SALE_TITLE));
                dialog.setHeaderText(null);
                dialog.setContentText(message(NO_EXISTING_SALE, presenter().dbPath()));
                dialog.initModality(WINDOW_MODAL);
                dialog.initOwner(presenter().mainStage());
                dialog.showAndWait();
            } else {
                ChoiceDialog<Integer> saleChooser = new ChoiceDialog<>(existingSales.get(0), existingSales);
                saleChooser.setTitle(message(OPEN_EXISTING_SALE));
                saleChooser.setHeaderText(null);
                saleChooser.setContentText(message(SELECT_EXISTING_SALE));
                saleChooser.initModality(WINDOW_MODAL);
                saleChooser.initOwner(presenter().mainStage());
                saleChooser.showAndWait().ifPresent(result -> {
                    presenter().setCurrentSale(result);
                    populateOpenSale();
                    pane.setContent(openedSale);
                });
            }
        });
        start.setOnAction(event -> {
            boolean started = presenter().startNewSale();
            if (started) {
                populateOpenSale();
                pane.setContent(openedSale);
            }
        });
        return vBox;
    }

    private void populateOpenSale() {

    }

    TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }

    boolean overwriteExistingYear() {
        ButtonType yes = new ButtonType(message(YES), ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(message(NO), ButtonBar.ButtonData.NO);
        Alert dialog = new Alert(CONFIRMATION, "", yes, no);
        dialog.setTitle(message(ARE_YOU_SURE));
        dialog.setHeaderText(null);
        dialog.setContentText(message(YEAR_ALREADY_EXISTS, now().getYear() + ""));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(presenter().mainStage());

        Optional<ButtonType> buttonType = dialog.showAndWait();
        return buttonType.map(ButtonType::getButtonData)
                         .orElse(OTHER)
                         .equals(ButtonBar.ButtonData.YES);
    }
}