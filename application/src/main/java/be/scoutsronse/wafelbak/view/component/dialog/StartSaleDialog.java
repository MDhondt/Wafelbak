package be.scoutsronse.wafelbak.view.component.dialog;

import be.scoutsronse.wafelbak.domain.dto.dialog.StartSale;
import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.ClusterState;
import be.scoutsronse.wafelbak.domain.entity.Sale;
import be.scoutsronse.wafelbak.i18n.MessageTag;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.lang.Double.MAX_VALUE;
import static java.lang.String.join;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static javafx.scene.layout.Priority.ALWAYS;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class StartSaleDialog extends Dialog<StartSale> {

    private final GridPane grid;
    private final Label salesManLabel;
    private final Label contactLabel;
    private final Label salesTeamLabel;
    private final Label amountLabel;
    private final Label amountInfoLabel;
    private final Label startTimeLabel;
    private final TextField salesManField;
    private final TextField contactField;
    private final TextArea teamField;
    private final Spinner<Integer> amountField;
    private final TextField startTimeField;

    public StartSaleDialog(BiFunction<MessageTag, Object[], String> messageSource, Cluster cluster) {
        final DialogPane dialogPane = getDialogPane();

        salesManField = new TextField();
        contactField = new TextField();
        teamField = new TextArea();
        amountField = numberSpinner();
        startTimeField = new TextField();
        salesManField.setMaxWidth(MAX_VALUE);
        contactField.setMaxWidth(MAX_VALUE);
        teamField.setMaxWidth(MAX_VALUE);
        amountField.setMaxWidth(MAX_VALUE);
        startTimeField.setMaxWidth(MAX_VALUE);
        GridPane.setHgrow(salesManField, ALWAYS);
        GridPane.setHgrow(contactField, ALWAYS);
        GridPane.setHgrow(teamField, ALWAYS);
        GridPane.setHgrow(amountField, ALWAYS);
        GridPane.setHgrow(startTimeField, ALWAYS);
        GridPane.setFillWidth(salesManField, true);
        GridPane.setFillWidth(contactField, true);
        GridPane.setFillWidth(teamField, true);
        GridPane.setFillWidth(amountField, true);
        GridPane.setFillWidth(startTimeField, true);
        teamField.prefWidthProperty().bind(salesManField.widthProperty());
        teamField.prefHeightProperty().bind(salesManField.heightProperty().multiply(3));

        Integer allTimeAmount = cluster.states().stream().sorted(comparing(ClusterState::year).reversed()).skip(1).map(ClusterState::sales).flatMap(Collection::stream).mapToInt(Sale::amount).sum();
        Integer lastAmount = cluster.states().stream().sorted(comparing(ClusterState::year).reversed()).skip(1).findFirst().map(ClusterState::sales).map(Collection::stream).map(str -> str.mapToInt(Sale::amount).sum()).orElse(0);
        Integer numberOfSales = (int) cluster.states().stream().sorted(comparing(ClusterState::year).reversed()).skip(1).filter(state -> state.sales().stream().mapToInt(Sale::amount).sum() > 0).count();
        Integer averageAmount = numberOfSales != 0 ? allTimeAmount / numberOfSales : 0;
        salesManLabel = createContentLabel(messageSource.apply(SALESMAN, new Object[0]));
        contactLabel = createContentLabel(messageSource.apply(CONTACT, new Object[0]));
        salesTeamLabel = createContentLabel(messageSource.apply(SALESTEAM, new Object[0]));
        amountLabel = createContentLabel(messageSource.apply(AMOUNT, new Object[0]));
        startTimeLabel = createContentLabel(messageSource.apply(START_TIME, new Object[0]));
        amountInfoLabel = createContentLabel(messageSource.apply(AMOUNT_INFO, new String[]{lastAmount.toString(), averageAmount.toString()}));

        grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(5);
        grid.setMaxWidth(MAX_VALUE);
        grid.setAlignment(CENTER_LEFT);

        ButtonType startButton = new ButtonType(messageSource.apply(START, new Object[0]), OK_DONE);
        ButtonType cancelButton = new ButtonType(messageSource.apply(CANCEL, new Object[0]), CANCEL_CLOSE);

        ChangeListener validationListener = getListener(dialogPane, startButton);
        salesManField.textProperty().addListener(validationListener);
        amountField.valueProperty().addListener(validationListener);
        startTimeField.textProperty().addListener(validationListener);

        setTitle(messageSource.apply(START_SALE_TITLE, new String[]{cluster.name()}));
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(startButton, cancelButton);

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("/img/ico.png"));

        updateGrid();

        setResultConverter(dialogButton -> {
            ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            if (data == OK_DONE) {
                return new StartSale(amountField.getValue(),
                                     salesManField.getText(),
                                     contactField.getText(),
                                     asList(teamField.getText().split("\n")),
                                     LocalTime.parse(startTimeField.getText(), ofPattern("HH:mm")));
            }
            return null;
        });

        runLater(() -> validationListener.changed(null, null, null));
    }

    public StartSaleDialog(BiFunction<MessageTag, Object[], String> messageSource, Cluster cluster, StartSale startSale) {
        this(messageSource, cluster);
        salesManField.setText(startSale.getSalesMan());
        contactField.setText(startSale.getContact());
        teamField.setText(join("\n", startSale.getSalesTeam()));
        amountField.getValueFactory().setValue(startSale.getAmount());
        startTimeField.setText(startSale.getStartTime().toLocalTime().format(ofPattern("HH:mm")));

        ButtonType updateButton = new ButtonType(messageSource.apply(SAVE, new Object[0]), OK_DONE);
        ButtonType cancelButton = new ButtonType(messageSource.apply(CANCEL, new Object[0]), CANCEL_CLOSE);
        ChangeListener validationListener = getListener(getDialogPane(), updateButton);
        salesManField.textProperty().addListener(validationListener);
        amountField.valueProperty().addListener(validationListener);
        startTimeField.textProperty().addListener(validationListener);
        getDialogPane().getButtonTypes().clear();
        getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);
    }

    private ChangeListener getListener(DialogPane dialogPane, ButtonType startButton) {
        return (observable, oldValue, newValue) -> {
            if (!isBlank(salesManField.getText()) && amountField.getValue() > 0) {
                try {
                    LocalTime.parse(startTimeField.getText(), ofPattern("HH:mm"));
                    dialogPane.lookupButton(startButton).setDisable(false);
                    return;
                } catch (DateTimeParseException dtpe) {
                }
            }
            dialogPane.lookupButton(startButton).setDisable(true);
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

    private Spinner<Integer> numberSpinner() {
        final Spinner<Integer> spinner = new Spinner<>();

        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<>(new IntegerStringConverter(), 50, filter);

        spinner.setValueFactory(new IntegerSpinnerValueFactory(0, 10000, 50));
        spinner.setEditable(true);
        spinner.getEditor().setTextFormatter(priceFormatter);

        return spinner;
    }

    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(salesManLabel, 0, 0);
        grid.add(salesManField, 1, 0);
        grid.add(contactLabel, 0, 1);
        grid.add(contactField, 1, 1);
        grid.add(salesTeamLabel, 0, 2);
        grid.add(teamField, 1, 2);
        grid.add(amountLabel, 0, 4);
        grid.add(amountField, 1, 4);
        grid.add(amountInfoLabel, 1, 5);
        grid.add(startTimeLabel, 0, 6);
        grid.add(startTimeField, 1, 6);

        startTimeField.setText(LocalTime.now().format(ofPattern("HH:mm")));

        getDialogPane().setContent(grid);

        runLater(salesManField::requestFocus);
    }
}