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
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.domain.ClusterStatus.NOT_STARTED;
import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static be.scoutsronse.wafelbak.tech.util.FXUtils.executeOnFXThread;
import static java.time.LocalDate.now;
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
        executeOnFXThread(() -> {
            openedSale = new VBox();
            openedSale.setAlignment(TOP_CENTER);

            HBox notStarted = new HBox();
            notStarted.setAlignment(CENTER);

            StackPane stackPane = new StackPane();
            Rectangle rectangle = new Rectangle(200, 250, Paint.valueOf("#235478"));
            rectangle.setArcHeight(10);
            rectangle.setArcWidth(10);
            DropShadow shadow = new DropShadow(5, 1, 1, BLACK);
            rectangle.setEffect(shadow);

            SearchableClusterTreeView notStartedTree = new SearchableClusterTreeView(streets -> System.out.println("TODO"));
            notStartedTree.setContentOpacity(0.8);
            notStartedTree.setContent(presenter.getClustersFor(NOT_STARTED));
            notStartedTree.minHeightProperty().bind(rectangle.heightProperty().subtract(6));
            notStartedTree.prefHeightProperty().bind(rectangle.heightProperty().subtract(6));
            notStartedTree.maxHeightProperty().bind(rectangle.heightProperty().subtract(6));
            notStartedTree.minWidthProperty().bind(rectangle.widthProperty().subtract(4));
            notStartedTree.prefWidthProperty().bind(rectangle.widthProperty().subtract(4));
            notStartedTree.maxWidthProperty().bind(rectangle.widthProperty().subtract(4));

            stackPane.getChildren().addAll(rectangle, notStartedTree);

            notStarted.getChildren().add(stackPane);
            openedSale.getChildren().add(notStarted);
        });
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