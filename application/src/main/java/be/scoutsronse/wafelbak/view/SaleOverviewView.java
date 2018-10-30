package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.dto.dialog.EndSale;
import be.scoutsronse.wafelbak.domain.dto.dialog.StartSale;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import be.scoutsronse.wafelbak.presenter.SaleOverviewPresenter;
import be.scoutsronse.wafelbak.view.component.dialog.EndSaleDialog;
import be.scoutsronse.wafelbak.view.component.dialog.StartSaleDialog;
import be.scoutsronse.wafelbak.view.component.treeview.SearchableClusterTreeView;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.domain.ClusterStatus.*;
import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static be.scoutsronse.wafelbak.tech.util.ConsumerUtils.combine;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
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
        openedSale = new VBox(40);
        openedSale.setAlignment(TOP_CENTER);

        openedSale.setOnMouseClicked(event -> presenter.selectAll());

        HBox notStarted = new HBox();
        notStarted.setAlignment(CENTER);

        StackPane notStartedStackPane = new StackPane();
        Rectangle notStartedRectangle = new Rectangle(155, 225);
        notStartedRectangle.fillProperty().bind(presenter.getNotStartedColour());
        notStartedRectangle.setArcHeight(10);
        notStartedRectangle.setArcWidth(10);
        DropShadow shadow = new DropShadow(5, 1, 1, BLACK);
        notStartedRectangle.setEffect(shadow);

        SearchableClusterTreeView notStartedTree = new SearchableClusterTreeView(presenter::selectNotStartedStreets);
        notStartedTree.setContentOpacity(0.8);
        notStartedTree.setContent(presenter.getClustersFor(NOT_STARTED));
        notStartedTree.minHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.prefHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.maxHeightProperty().bind(notStartedRectangle.heightProperty().subtract(6));
        notStartedTree.minWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));
        notStartedTree.prefWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));
        notStartedTree.maxWidthProperty().bind(notStartedRectangle.widthProperty().subtract(4));

        notStartedStackPane.getChildren().addAll(notStartedRectangle, notStartedTree);
        VBox notStartedTitledBox = new VBox(6);
        notStartedTitledBox.setAlignment(TOP_CENTER);
        notStartedTitledBox.getChildren().addAll(new Label(message(SALE_OVERVIEW_NOT_STARTED)), notStartedStackPane);
        notStarted.getChildren().add(notStartedTitledBox);

        HBox busy = new HBox();
        busy.setAlignment(CENTER);

        StackPane busyStackPane = new StackPane();
        Rectangle busyRectangle = new Rectangle(155, 225);
        busyRectangle.fillProperty().bind(presenter.getBusyColour());
        busyRectangle.setArcHeight(10);
        busyRectangle.setArcWidth(10);
        busyRectangle.setEffect(shadow);

        SearchableClusterTreeView busyTree = new SearchableClusterTreeView(presenter::selectBusyStreets);
        busyTree.setContentOpacity(0.8);
        busyTree.setContent(presenter.getClustersFor(BUSY));
        busyTree.minHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.prefHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.maxHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        busyTree.minWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        busyTree.prefWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        busyTree.maxWidthProperty().bind(busyRectangle.widthProperty().subtract(4));

        busyStackPane.getChildren().addAll(busyRectangle, busyTree);
        VBox busyTitledBox = new VBox(6);
        busyTitledBox.setAlignment(TOP_CENTER);
        busyTitledBox.getChildren().addAll(new Label(message(SALE_OVERVIEW_BUSY)), busyStackPane);
        busy.getChildren().add(busyTitledBox);

        HBox done = new HBox(10);
        done.setAlignment(CENTER);

        StackPane partlyDoneStackPane = new StackPane();
        Rectangle partlyDoneRectangle = new Rectangle(155, 225);
        partlyDoneRectangle.fillProperty().bind(presenter.getDoneColour());
        partlyDoneRectangle.setArcHeight(10);
        partlyDoneRectangle.setArcWidth(10);
        partlyDoneRectangle.setEffect(shadow);

        SearchableClusterTreeView partlyDoneTree = new SearchableClusterTreeView(null);
        partlyDoneTree.setContentOpacity(0.8);
        partlyDoneTree.setContent(presenter.getClustersFor(PARTLY_DONE));
        partlyDoneTree.minHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        partlyDoneTree.prefHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        partlyDoneTree.maxHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        partlyDoneTree.minWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        partlyDoneTree.prefWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        partlyDoneTree.maxWidthProperty().bind(busyRectangle.widthProperty().subtract(4));

        partlyDoneStackPane.getChildren().addAll(partlyDoneRectangle, partlyDoneTree);
        VBox partlyDoneTitledBox = new VBox(6);
        partlyDoneTitledBox.setAlignment(TOP_CENTER);
        partlyDoneTitledBox.getChildren().addAll(new Label(message(SALE_OVERVIEW_PARTLY_DONE)), partlyDoneStackPane);

        StackPane doneStackPane = new StackPane();
        Rectangle doneRectangle = new Rectangle(155, 225);
        doneRectangle.fillProperty().bind(presenter.getDoneColour());
        doneRectangle.setArcHeight(10);
        doneRectangle.setArcWidth(10);
        doneRectangle.setEffect(shadow);

        SearchableClusterTreeView doneTree = new SearchableClusterTreeView(presenter::selectDoneStreets);
        doneTree.setContentOpacity(0.8);
        doneTree.setContent(presenter.getClustersFor(DONE));
        doneTree.minHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        doneTree.prefHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        doneTree.maxHeightProperty().bind(busyRectangle.heightProperty().subtract(6));
        doneTree.minWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        doneTree.prefWidthProperty().bind(busyRectangle.widthProperty().subtract(4));
        doneTree.maxWidthProperty().bind(busyRectangle.widthProperty().subtract(4));

        doneStackPane.getChildren().addAll(doneRectangle, doneTree);
        VBox doneTitledBox = new VBox(6);
        doneTitledBox.setAlignment(TOP_CENTER);
        doneTitledBox.getChildren().addAll(new Label(message(SALE_OVERVIEW_DONE)), doneStackPane);

        done.getChildren().addAll(partlyDoneTitledBox, doneTitledBox);

        notStartedTree.setSelectionConsumer(combine(s -> busyTree.clearSelection(), s -> partlyDoneTree.clearSelection(), s -> doneTree.clearSelection(), presenter::selectNotStartedStreets));
        busyTree.setSelectionConsumer(combine(s -> notStartedTree.clearSelection(), s -> partlyDoneTree.clearSelection(), s -> doneTree.clearSelection(), presenter::selectBusyStreets));
        partlyDoneTree.setSelectionConsumer(combine(s -> busyTree.clearSelection(), s -> notStartedTree.clearSelection(), s -> doneTree.clearSelection(), presenter::selectPartlyDoneStreets));
        doneTree.setSelectionConsumer(combine(s -> busyTree.clearSelection(), s -> partlyDoneTree.clearSelection(), s -> notStartedTree.clearSelection(), presenter::selectDoneStreets));

        notStartedTree.setAllowedDragSources(singletonList(Triple.of(busyTree, x -> undoStart(), this::undoStart)),
                                             null);
        busyTree.setAllowedDragSources(asList(Triple.of(notStartedTree, x -> startSaleDialog(notStartedTree.getSelectedClusterId()), x -> {}),
                                              Triple.of(partlyDoneTree, x -> startSaleDialog(partlyDoneTree.getSelectedClusterId()), x -> {})),
                                       this::updateBusySaleDialog);
        partlyDoneTree.setAllowedDragSources(singletonList(Triple.of(busyTree, x -> endSaleDialog(busyTree.getSelectedClusterId(), false), x -> {})),
                                             null);
        doneTree.setAllowedDragSources(singletonList(Triple.of(busyTree, x -> endSaleDialog(busyTree.getSelectedClusterId(), true), x -> {})),
                                       this::updateDoneSaleDialog);

        openedSale.getChildren().addAll(notStarted, busy, done);
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

    private boolean startSaleDialog(ClusterId selectedClusterId) {
        StartSaleDialog dialog = new StartSaleDialog(this::message, presenter.getClusterFor(selectedClusterId));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);
        Optional<StartSale> startSale = dialog.showAndWait();

        if (startSale.isPresent()) {
            presenter.startSale(selectedClusterId, startSale.get());
            return true;
        }
        return false;
    }

    private void updateBusySaleDialog(ClusterId clusterId) {
        StartSaleDialog dialog = new StartSaleDialog(this::message, presenter.getClusterFor(clusterId), presenter.getBusySaleFor(clusterId));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);
        Optional<StartSale> updateSale = dialog.showAndWait();

        updateSale.ifPresent(startSale -> presenter.updateSale(clusterId, startSale));
    }

    private void updateDoneSaleDialog(ClusterId clusterId) {
        EndSaleDialog dialog = new EndSaleDialog(this::message, presenter.getClusterFor(clusterId), presenter.getEndSaleFor(clusterId));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);
        Optional<EndSale> updateSale = dialog.showAndWait();

        updateSale.ifPresent(endSale -> presenter.updateSale(clusterId, endSale));
    }

    private boolean endSaleDialog(ClusterId selectedClusterId, boolean fullyDone) {
        EndSaleDialog dialog = new EndSaleDialog(this::message, presenter.getClusterFor(selectedClusterId), !fullyDone);
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);
        Optional<EndSale> endSale = dialog.showAndWait();

        if (endSale.isPresent()) {
            presenter.endSale(selectedClusterId, endSale.get(), fullyDone ? DONE : PARTLY_DONE);
            return true;
        }
        return false;
    }

    private boolean undoStart() {
        ButtonType yes = new ButtonType(message(YES), ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(message(NO), ButtonBar.ButtonData.NO);
        Alert dialog = new Alert(CONFIRMATION, "", yes, no);
        dialog.setTitle(message(ARE_YOU_SURE));
        dialog.setHeaderText(null);
        dialog.setContentText(message(REMOVE_BUSY));
        dialog.initModality(WINDOW_MODAL);
        dialog.initOwner(mainStage);

        return dialog.showAndWait()
                     .map(ButtonType::getButtonData)
                     .orElse(OTHER)
                     .equals(ButtonBar.ButtonData.YES);
    }

    private void undoStart(ClusterDto clusterDto) {
        presenter.undoStart(clusterDto.id);
    }
}