package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.domain.dto.ClusterDetail;
import be.scoutsronse.wafelbak.domain.entity.UndocumentedSale;
import be.scoutsronse.wafelbak.presenter.DetailsPresenter;
import be.scoutsronse.wafelbak.view.component.dialog.UndocumentedSaleDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.MessageSource;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.lang.Double.MAX_VALUE;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.stage.Modality.WINDOW_MODAL;

public class DetailsView extends AbstractView {

    private DetailsPresenter presenter;
    private TitledPane pane;
    private VBox content;
    private Stage mainStage;
    private TableView<ClusterDetail> table;
    private ObservableList<ClusterDetail> tableContent = observableArrayList();
    private Label totalAmount = new Label("0");
    private Label totalMoney = new Label("0");
    private Label totalLooseAmount = new Label("0");
    private Label totalLooseMoney = new Label("0");
    private Label totalSaleAmount = new Label("0");
    private Label totalSaleMoney = new Label("0");

    public DetailsView(DetailsPresenter presenter, MessageSource messageSource, Stage mainStage) {
        super(messageSource);
        this.presenter = presenter;
        this.mainStage = mainStage;

        content = new VBox(10);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(5);
        grid.setMaxWidth(MAX_VALUE);
        grid.setAlignment(CENTER_LEFT);
        grid.add(new Label(message(TOTAL_SALE_AMOUNT)), 0, 0);
        grid.add(new Label(message(TOTAL_SALE_MONEY)), 0, 1);
        grid.add(new Label(message(TOTAL_LOOSE_AMOUNT)), 0, 2);
        grid.add(new Label(message(TOTAL_LOOSE_MONEY)), 0, 3);
        grid.add(new Label(message(TOTAL_AMOUNT)), 0, 4);
        grid.add(new Label(message(TOTAL_MONEY)), 0, 5);
        grid.add(totalSaleAmount, 1, 0);
        grid.add(totalSaleMoney, 1, 1);
        grid.add(totalLooseAmount, 1, 2);
        grid.add(totalLooseMoney, 1, 3);
        grid.add(totalAmount, 1, 4);
        grid.add(totalMoney, 1, 5);

        Button add = new Button(message(ADD));
        add.setOnMouseReleased(event -> {
            UndocumentedSaleDialog dialog = new UndocumentedSaleDialog(this::message);
            dialog.initModality(WINDOW_MODAL);
            dialog.initOwner(mainStage);
            Optional<Pair<Integer, Float>> sale = dialog.showAndWait();

            sale.ifPresent(presenter::addUndocumentedSale);
        });
        add.prefHeightProperty().bind(totalLooseAmount.heightProperty().add(totalLooseMoney.heightProperty().add(5)));
        grid.add(add, 2, 2, 1, 2);

        table = new TableView<>();
        TableColumn<ClusterDetail, String> nameColumn = new TableColumn<>(message(NAME_COLUMN));
        TableColumn<ClusterDetail, String> amountColumn = new TableColumn<>(message(AMOUNT_COLUMN));
        TableColumn<ClusterDetail, String> moneyColumn = new TableColumn<>(message(MONEY_COLUMN));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClusterName()));
        amountColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSold().toString()));
        moneyColumn.setCellValueFactory(param -> new SimpleStringProperty(new DecimalFormat("0.00").format(param.getValue().getMoney())));
        table.getColumns().addAll(nameColumn, amountColumn, moneyColumn);
        table.setItems(tableContent);
        table.prefHeightProperty().bind(content.heightProperty());
        table.prefWidthProperty().bind(content.widthProperty());
        nameColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
        amountColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
        moneyColumn.prefWidthProperty().bind(table.widthProperty().divide(3));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                presenter.select(newValue.getClusterId());
            }
        });

        content.getChildren().addAll(grid, table);

        pane = new TitledPane(message(DETAILS_TITLE), content);
        pane.setContent(content);
    }

    public TitledPane getPane() {
        return pane;
    }

    public void populate(List<ClusterDetail> details, UndocumentedSale undocumentedSale) {
        tableContent.clear();
        tableContent.addAll(details);
        int saleAmount = details.stream().mapToInt(ClusterDetail::getSold).sum();
        double saleMoney = details.stream().map(ClusterDetail::getMoney).mapToDouble(Float::doubleValue).sum();
        totalSaleAmount.setText(Integer.toString(saleAmount));
        totalSaleMoney.setText("€ " + new DecimalFormat("0.00").format(saleMoney));
        if (undocumentedSale != null) {
            totalLooseAmount.setText(Integer.toString(undocumentedSale.getAmount()));
            totalLooseMoney.setText("€ " + new DecimalFormat("0.00").format(undocumentedSale.getMoney()));
            totalAmount.setText(Integer.toString(saleAmount + undocumentedSale.getAmount()));
            totalMoney.setText("€ " + new DecimalFormat("0.00").format(saleMoney + undocumentedSale.getMoney()));
        }
    }
}