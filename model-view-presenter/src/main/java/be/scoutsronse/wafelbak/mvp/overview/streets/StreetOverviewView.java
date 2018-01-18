package be.scoutsronse.wafelbak.mvp.overview.streets;

import be.scoutsronse.wafelbak.mvp.View;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

public class StreetOverviewView extends View<StreetOverviewPresenter> {

    private TitledPane pane;
    private TableView<StreetDto> streetOverviewTable;
    private TableView<ClusterData> overviewTable;

    public StreetOverviewView(StreetOverviewPresenter presenter) {
        super(presenter);
        pane = new TitledPane();
        streetOverviewTable = new TableView<>();
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
        pane.setContent(streetOverviewTable);
    }

    private void createOverviewTable() {

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