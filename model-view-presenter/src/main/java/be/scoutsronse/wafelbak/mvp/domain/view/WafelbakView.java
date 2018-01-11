package be.scoutsronse.wafelbak.mvp.domain.view;

import be.scoutsronse.wafelbak.map.MapView;
import be.scoutsronse.wafelbak.mvp.View;
import be.scoutsronse.wafelbak.mvp.domain.presenter.WafelbakPresenter;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;

    public WafelbakView(WafelbakPresenter presenter) {
        super(presenter);
    }

    public void show() {
        populateStage();
        primaryStage.show();
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void populateStage() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new MapView());
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
    }
}