package be.scoutsronse.wafelbak.view.component.dialog;

import be.scoutsronse.wafelbak.i18n.MessageTag;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.BiFunction;

import static be.scoutsronse.wafelbak.i18n.MessageTag.*;
import static java.lang.Double.MAX_VALUE;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static javafx.scene.layout.Priority.ALWAYS;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@SuppressWarnings("Duplicates")
public class UndocumentedSaleDialog extends Dialog<Pair<Integer, Float>> {

    private final GridPane grid;
    private final Label amountLabel;
    private final Label moneyLabel;
    private final TextField amountField;
    private final TextField moneyField;

    public UndocumentedSaleDialog(BiFunction<MessageTag, Object[], String> messageSource) {
        final DialogPane dialogPane = getDialogPane();

        amountLabel = createContentLabel(messageSource.apply(AMOUNT, new Object[0]));
        moneyLabel = createContentLabel(messageSource.apply(MONEY_IN, new Object[0]));
        amountField = new TextField();
        moneyField = new TextField();
        GridPane.setHgrow(amountField, ALWAYS);
        GridPane.setHgrow(moneyField, ALWAYS);
        GridPane.setFillWidth(amountField, true);
        GridPane.setFillWidth(moneyField, true);
        grid = new GridPane();

        grid.setHgap(15);
        grid.setVgap(5);
        grid.setMaxWidth(MAX_VALUE);
        grid.setAlignment(CENTER_LEFT);

        ButtonType saveButton = new ButtonType(messageSource.apply(SAVE, new Object[0]), OK_DONE);
        ButtonType cancelButton = new ButtonType(messageSource.apply(CANCEL, new Object[0]), CANCEL_CLOSE);

        ChangeListener validationListener = getListener(dialogPane, saveButton);
        amountField.textProperty().addListener(validationListener);
        moneyField.textProperty().addListener(validationListener);

        setTitle(messageSource.apply(LOOSE_TITLE, new Object[0]));
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(saveButton, cancelButton);

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("/img/ico.png"));

        updateGrid();

        setResultConverter(dialogButton -> {
            ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            if (data == OK_DONE) {
                return Pair.of(Integer.parseInt(amountField.getText()), Float.parseFloat(moneyField.getText()));
            }
            return null;
        });

        runLater(() -> validationListener.changed(null, null, null));
    }

    private ChangeListener getListener(DialogPane dialogPane, ButtonType startButton) {
        return (observable, oldValue, newValue) -> {
            if (dialogPane.lookupButton(startButton) != null) {
                try {
                    int i = Integer.parseInt(amountField.getText());
                    float f = Float.parseFloat(moneyField.getText());
                    if (!isBlank(amountField.getText()) && !isBlank(moneyField.getText()) && i > 0 && f > 0) {
                        dialogPane.lookupButton(startButton).setDisable(false);
                        return;
                    }
                } catch (Exception dtpe) {
                }
                dialogPane.lookupButton(startButton).setDisable(true);
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

        grid.add(amountLabel, 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(moneyLabel, 0, 1);
        grid.add(moneyField, 1, 1);

        getDialogPane().setContent(grid);

        runLater(amountField::requestFocus);
    }
}