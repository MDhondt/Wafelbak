package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.presenter.SaleOverviewPresenter;
import be.scoutsronse.wafelbak.view.component.treeview.SearchableClusterTreeView;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.domain.ClusterStatus.NOT_STARTED;
import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.ButtonBar.ButtonData.OTHER;
import static javafx.scene.paint.Color.BLACK;
import static javafx.stage.Modality.WINDOW_MODAL;

public class SaleOverviewView extends AbstractView {

    private SaleOverviewPresenter presenter;
    private TitledPane pane;
    private VBox unopenedSale;
    private VBox openedSale;
    private Stage mainStage;

    public SaleOverviewView(SaleOverviewPresenter presenter, MessageSource messageSource, Stage mainStage) {
        super(messageSource);
        this.presenter = presenter;
        this.mainStage = mainStage;

        unopenedSale = createUnopenedSale();

        pane = new TitledPane(message(SALE_OVERVIEW_TITLE), unopenedSale);
        pane.setContent(unopenedSale);
    }

    private VBox createUnopenedSale() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(TOP_CENTER);
        Button open = new Button(message(OPEN_SALE));
        Button start = new Button(message(START_SALE));
        VBox empty = new VBox();
        empty.setPrefHeight(20);
        vBox.getChildren().addAll(empty, open, start);

        open.prefWidthProperty().bind(vBox.widthProperty().subtract(100));
        start.prefWidthProperty().bind(vBox.widthProperty().subtract(100));

        open.setOnAction(event -> {
            List<Integer> existingSales = presenter.existingSales();
            if (existingSales.isEmpty()) {
                Alert dialog = new Alert(INFORMATION);
                dialog.setTitle(message(NO_EXISTING_SALE_TITLE));
                dialog.setHeaderText(null);
                dialog.setContentText(message(NO_EXISTING_SALE, presenter.dbPath()));
                dialog.initModality(WINDOW_MODAL);
                dialog.initOwner(mainStage);
                dialog.showAndWait();
            } else {
                ChoiceDialog<Integer> saleChooser = new ChoiceDialog<>(existingSales.get(0), existingSales);
                saleChooser.setTitle(message(OPEN_EXISTING_SALE));
                saleChooser.setHeaderText(null);
                saleChooser.setContentText(message(SELECT_EXISTING_SALE));
                saleChooser.initModality(WINDOW_MODAL);
                saleChooser.initOwner(mainStage);
                saleChooser.showAndWait().ifPresent(result -> {
                    presenter.setCurrentSale(result);
                    populateOpenSale();
                    pane.setContent(openedSale);
                });
            }
        });

        start.setOnAction(event -> {
            boolean started = presenter.startNewSale();
            if (started) {
                populateOpenSale();
                pane.setContent(openedSale);
            }
        });

        return vBox;
    }

    private void populateOpenSale() {
        openedSale = new VBox();
        openedSale.setAlignment(TOP_CENTER);

        HBox notStarted = new HBox();
        notStarted.setAlignment(CENTER);

        StackPane notStartedStackPane = new StackPane();
        Rectangle notStartedRectangle = new Rectangle(155, 250);
        notStartedRectangle.fillProperty().bind(presenter.getNotStartedColour());
        notStartedRectangle.setArcHeight(10);
        notStartedRectangle.setArcWidth(10);
        DropShadow shadow = new DropShadow(5, 1, 1, BLACK);
        notStartedRectangle.setEffect(shadow);

        SearchableClusterTreeView notStartedTree = new SearchableClusterTreeView(streets -> System.out.println("Selected 'notStarted' item"));
        notStartedTree.setContentOpacity(0.8);
        notStartedTree.setContent(presenter.getClustersFor(NOT_STARTED));
        notStartedTree.setAllowedDragSources(emptyList());
        notStartedTree.minHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.prefHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.maxHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.minWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));
        notStartedTree.prefWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));
        notStartedTree.maxWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));

        notStartedStackPane.getChildren().addAll(notStartedRectangle, notStartedTree);
        notStarted.getChildren().add(notStartedStackPane);

        HBox busyOrDone = new HBox(10);
        busyOrDone.setAlignment(CENTER);

        StackPane busyStackPane = new StackPane();
        Rectangle busyRectangle = new Rectangle(155, 250, Paint.valueOf("#772f22"));
        busyRectangle.setArcHeight(10);
        busyRectangle.setArcWidth(10);
        busyRectangle.setEffect(shadow);

        SearchableClusterTreeView busyTree = new SearchableClusterTreeView(streets -> System.out.println("Selected 'busy' item"));
        busyTree.setContentOpacity(0.8);
        busyTree.setContent(presenter.getClustersFor(NOT_STARTED)); // TODO change to NOT_STARTED to BUSY
        busyTree.setAllowedDragSources(singletonList(Triple.of(notStartedTree, x -> true, x -> {})));
        busyTree.minHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.prefHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.maxHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.minWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        busyTree.prefWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        busyTree.maxWidthProperty().bind(busyRectangle.widthProperty().subtract(4));

        busyStackPane.getChildren().addAll(busyRectangle, busyTree);

        busyOrDone.getChildren().addAll(notStartedStackPane, busyStackPane);
        openedSale.getChildren().addAll(notStarted, busyOrDone);
    }

    public TitledPane getPane() {
        return pane;
    }

    public boolean confirmOverwriteExistingYear() {
        ButtonType yes = new ButtonType(message(YES), ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(message(NO), ButtonBar.ButtonData.NO);
        Alert dialog = new Alert(CONFIRMATION, "", yes, no);
        dialog.setTitle(message(ARE_YOU_SURE));
        dialog.setHeaderText(null);
        dialog.setContentText(message(YEAR_ALREADY_EXISTS, now().getYear() + ""));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);

        Optional<ButtonType> buttonType = dialog.showAndWait();
        return buttonType.map(ButtonType::getButtonData)
                         .orElse(OTHER)
                         .equals(ButtonBar.ButtonData.YES);
    }
}