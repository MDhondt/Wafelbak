package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class StreetOverviewView extends View<StreetOverviewPresenter> {

    private TitledPane pane;
    private TableView<StreetDto> streetOverviewTable;
    private TableView<ClusterData> overviewTable;

    public StreetOverviewView(StreetOverviewPresenter presenter) {
        super(presenter);
        pane = new TitledPane();
        streetOverviewTable = new TableView<>();
        createOverviewTable();
        TableColumn<StreetDto, String> streetNameColumn = new TableColumn<>();
        streetNameColumn.setCellValueFactory(cellData -> cellData.getValue().name());
        streetOverviewTable.getColumns().add(streetNameColumn);
        streetOverviewTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StreetDto>() {
            @Override
            public void changed(ObservableValue<? extends StreetDto> observable, StreetDto oldValue, StreetDto newValue) {
                if (newValue != null) {
                    presenter().selectStreets(newValue.coordinateLines());
                }
            }
        });
        Button button = new Button("persist");
        button.setOnAction(event -> presenter().persist());
        VBox vBox = new VBox();
        vBox.getChildren().addAll(button, overviewTable, streetOverviewTable);
        pane.setContent(vBox);
    }

    private void createOverviewTable() {
        overviewTable = new TableView<>();
        TableColumn<ClusterData, String> column = new TableColumn<>();
        column.setCellValueFactory(cellData -> cellData.getValue().name());
        overviewTable.getColumns().add(column);
        overviewTable.getSortOrder().add(column);
    }

    public TitledPane getPane() {
        return pane;
    }

    StringProperty titleProperty() {
        return pane.textProperty();
    }

    TableView<ClusterData> overviewTable() {
        return overviewTable;
    }

    TableView<StreetDto> streetOverviewTable() {
        return streetOverviewTable;
    }
}