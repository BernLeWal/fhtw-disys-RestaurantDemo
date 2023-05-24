package at.fhtw.disys.orderui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderUIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OrderUIApplication.class.getResource("kitchenui-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 260);
        stage.setTitle("KitchenUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}