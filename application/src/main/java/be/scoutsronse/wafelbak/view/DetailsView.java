package be.scoutsronse.wafelbak.view;

import be.scoutsronse.wafelbak.domain.dto.ClusterDetail;
import be.scoutsronse.wafelbak.presenter.DetailsPresenter;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.springframework.context.MessageSource;

import static be.scoutsronse.wafelbak.i18n.MessageTag.DETAILS_TITLE;

public class DetailsView extends AbstractView {

    private DetailsPresenter presenter;
    private TitledPane pane;
    private VBox content;
    private TableView<ClusterDetail> table;

    public DetailsView(DetailsPresenter presenter, MessageSource messageSource) {
        super(messageSource);
        this.presenter = presenter;

        content = new VBox();
        table = new TableView<>();
        table.setRowFactory(new Callback<TableView<ClusterDetail>, TableRow<ClusterDetail>>() {
            @Override
            public TableRow<ClusterDetail> call(TableView<ClusterDetail> param) {
                return null;
            }
        });

        pane = new TitledPane(message(DETAILS_TITLE), content);
        pane.setContent(content);
    }

    public TitledPane getPane() {
        return pane;
    }
}