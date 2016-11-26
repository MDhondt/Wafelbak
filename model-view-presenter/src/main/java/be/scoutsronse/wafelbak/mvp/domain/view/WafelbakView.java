package be.scoutsronse.wafelbak.mvp.domain.view;

import be.scoutsronse.wafelbak.entity.Street;
import be.scoutsronse.wafelbak.mvp.View;
import be.scoutsronse.wafelbak.mvp.domain.presenter.WafelbakPresenter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WafelbakView extends View<WafelbakPresenter> {

    private Stage primaryStage;

    public WafelbakView(WafelbakPresenter presenter) {
        super(presenter);
    }

    public void show(){
        primaryStage.show();
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        populateStage();
    }

    private void populateStage() {
        Button button = new Button();
        button.setText("Test DB");
        button.setOnAction(event -> {
            Street street = new Street();
            street.setName("Croix ou Pile");
            presenter().persist(street);
        });
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
    }
}