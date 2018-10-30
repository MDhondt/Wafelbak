package be.scoutsronse.wafelbak.view.component.dialog;

import be.scoutsronse.wafelbak.domain.dto.dialog.EndSale;
import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.domain.entity.Sale;
import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.i18n.MessageTag;
import be.scoutsronse.wafelbak.tech.util.PredicateUtils;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.lang.Double.MAX_VALUE;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static javafx.scene.layout.Priority.ALWAYS;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

@SuppressWarnings("Duplicates")
public class EndSaleDialog extends Dialog<EndSale> {

    private final GridPane grid;
    private final Label amountBackLabel;
    private final Label initialAmountLabel;
    private final Label moneyLabel;
    private final Label moneyLimitLabel;
    private final Label endLabel;
    private final Label doneStreetsLabel;
    private final TextField amountBackField;
    private final TextField moneyField;
    private final TextField endField;
    private final CheckListView<String> doneStreetsList;
    private final List<String> streetsThatNeededToBeDone;
    private final boolean streetsSelectable;
    private final Integer initialAmount;
    private final BiFunction<MessageTag, Object[], String> messageSource;

    public EndSaleDialog(BiFunction<MessageTag, Object[], String> messageSource, Cluster cluster, boolean streetsSelectable) {
        final DialogPane dialogPane = getDialogPane();
        this.streetsSelectable = streetsSelectable;
        this.messageSource = messageSource;

        amountBackField = new TextField();
        moneyField = new TextField();
        endField = new TextField();
        doneStreetsList = new CheckListView<>();
        amountBackField.setMaxWidth(MAX_VALUE);
        moneyField.setMaxWidth(MAX_VALUE);
        doneStreetsList.setMaxWidth(MAX_VALUE);
        endField.setMaxWidth(MAX_VALUE);
        GridPane.setHgrow(amountBackField, ALWAYS);
        GridPane.setHgrow(moneyField, ALWAYS);
        GridPane.setHgrow(endField, ALWAYS);
        GridPane.setHgrow(doneStreetsList, ALWAYS);
        GridPane.setFillWidth(amountBackField, true);
        GridPane.setFillWidth(moneyField, true);
        GridPane.setFillWidth(endField, true);
        GridPane.setFillWidth(doneStreetsList, true);
        doneStreetsList.prefWidthProperty().bind(amountBackField.widthProperty());
        doneStreetsList.prefHeightProperty().bind(amountBackField.heightProperty().multiply(6));

        ClusterState clusterState = cluster.states().stream().sorted(comparing(ClusterState::year).reversed()).findFirst().get();
        Sale sale = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).findFirst().get();
        List<Street> streetsThatWereAlreadyDone = clusterState.sales().stream().sorted(comparing(Sale::start).reversed()).skip(1).map(Sale::streets).flatMap(Collection::stream).collect(toList());
        streetsThatNeededToBeDone = cluster.streets().stream().filter(PredicateUtils.not(streetsThatWereAlreadyDone::contains)).map(Street::name).sorted().collect(toList());
        doneStreetsList.getItems().addAll(streetsThatNeededToBeDone);
        streetsThatNeededToBeDone.forEach(street -> doneStreetsList.getItemBooleanProperty(street).setValue(true));
        initialAmount = sale.amount();

        amountBackLabel = createContentLabel(messageSource.apply(AMOUNT_BACK, new Object[0]));
        initialAmountLabel = createContentLabel(messageSource.apply(INITIAL_AMOUNT, new String[]{initialAmount.toString(), initialAmount.toString()}));
        moneyLabel = createContentLabel(messageSource.apply(MONEY_IN, new Object[0]));
        moneyLimitLabel = createContentLabel(messageSource.apply(MONEY_LIMITS, new String[]{"0", "0"}));
        endLabel = createContentLabel(messageSource.apply(END_TIME, new Object[0]));
        doneStreetsLabel = createContentLabel(messageSource.apply(DONE_STREETS, new Object[0]));

        grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(5);
        grid.setMaxWidth(MAX_VALUE);
        grid.setAlignment(CENTER_LEFT);

        ButtonType endButton = new ButtonType(messageSource.apply(END, new Object[0]), OK_DONE);
        ButtonType cancelButton = new ButtonType(messageSource.apply(CANCEL, new Object[0]), CANCEL_CLOSE);

        ChangeListener validationListener = getListener(dialogPane, endButton);
        amountBackField.textProperty().addListener(validationListener);
        moneyField.textProperty().addListener(validationListener);
        endField.textProperty().addListener(validationListener);

        setTitle(messageSource.apply(END_SALE_TITLE, new String[]{cluster.name()}));
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.getStyleClass().addAll("text-input-dialog");
        dialogPane.getButtonTypes().addAll(endButton, cancelButton);

        Scene scene = dialogPane.getScene();
        Stage stage = (Stage) scene.getWindow();
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        stage.getIcons().add(new Image("/img/ico.png"));

        updateGrid();

        setResultConverter(dialogButton -> {
            ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            if (data == OK_DONE) {
                return new EndSale(Math.max(0, initialAmount - Integer.parseInt(amountBackField.getText())),
                                   Float.parseFloat(moneyField.getText()),
                                   LocalTime.parse(endField.getText(), ofPattern("HH:mm")),
                                   doneStreetsList.getCheckModel().getCheckedItems().stream().map(street -> cluster.streets().stream().filter(str -> str.name().equalsIgnoreCase(street)).findFirst().get()).map(Street::id).collect(toList()));
            }
            return null;
        });

        runLater(() -> validationListener.changed(null, null, null));
    }

    public EndSaleDialog(BiFunction<MessageTag, Object[], String> messageSource, Cluster cluster, EndSale endSale) {
        this(messageSource, cluster, false);
        amountBackField.setText("0");
        moneyField.setText(endSale.getMoney().toString());
        endField.setText(endSale.getEndTime().toLocalTime().format(ofPattern("HH:mm")));

        ButtonType updateButton = new ButtonType(messageSource.apply(SAVE, new Object[0]), OK_DONE);
        ButtonType cancelButton = new ButtonType(messageSource.apply(CANCEL, new Object[0]), CANCEL_CLOSE);
        ChangeListener validationListener = getListener(getDialogPane(), updateButton);
        amountBackField.textProperty().addListener(validationListener);
        moneyField.textProperty().addListener(validationListener);
        endField.textProperty().addListener(validationListener);
        getDialogPane().getButtonTypes().clear();
        getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);
    }

    private ChangeListener getListener(DialogPane dialogPane, ButtonType endButton) {
        return (observable, oldValue, newValue) -> {
            if (dialogPane.lookupButton(endButton) != null) {
                try {
                    int actualAmountSold = Math.max(0, initialAmount - Integer.parseInt(amountBackField.getText()));
                    String minMoney = minMoneyFor(actualAmountSold).toString();
                    String maxMoney = maxMoneyFor(actualAmountSold).toString();
                    initialAmountLabel.setText(messageSource.apply(INITIAL_AMOUNT, new String[]{initialAmount.toString(), Integer.toString(actualAmountSold)}));
                    moneyLimitLabel.setText(messageSource.apply(MONEY_LIMITS, new String[]{minMoney, maxMoney}));
                    double actualMoney = Double.parseDouble(moneyField.getText());
                    PseudoClass errorClass = PseudoClass.getPseudoClass("error");
                    if (actualMoney < Double.parseDouble(minMoney)) {
                        moneyField.pseudoClassStateChanged(errorClass, true);
                    } else {
                        moneyField.pseudoClassStateChanged(errorClass, false);
                    }
                    LocalTime.parse(endField.getText(), ofPattern("HH:mm"));
                    dialogPane.lookupButton(endButton).setDisable(false);
                    return;
                } catch (NumberFormatException | DateTimeParseException e) {
                }
                dialogPane.lookupButton(endButton).setDisable(true);
            }
        };
    }

    private Label createContentLabel(String text) {
        Label label = new Label(text);
        label.setMaxWidth(MAX_VALUE);
        label.setMaxHeight(MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(USE_COMPUTED_SIZE);
        return label;
    }

    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(amountBackLabel, 0,0);
        grid.add(amountBackField, 1, 0);
        grid.add(initialAmountLabel, 1, 1);
        grid.add(moneyLabel, 0, 2);
        grid.add(moneyField, 1,2);
        grid.add(moneyLimitLabel, 1, 3);
        grid.add(endLabel, 0, 4);
        grid.add(endField, 1, 4);
        if (streetsSelectable) {
            grid.add(doneStreetsLabel, 0, 5);
            grid.add(doneStreetsList, 1, 5);
        }

        endField.setText(LocalTime.now().format(ofPattern("HH:mm")));

        getDialogPane().setContent(grid);

        runLater(amountBackField::requestFocus);
    }

    private Integer minMoneyFor(Integer amount) {
        int boxes = amount / 25;
        int per3 = (amount - (25 * boxes)) / 3;
        int rest = amount - (25 * boxes) - (3 * per3);
        return 40 * boxes + 5 * per3 + 2 * rest;
    }

    private Integer maxMoneyFor(Integer amount) {
        return 2 * amount;
    }
}