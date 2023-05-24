package at.fhtw.disys.orderui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KitchenUIController {

    public TextField txtNewOrder;
    public TextField txtPanCount;

    public void btnStartOrder_Clicked(ActionEvent actionEvent) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/orders"))
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(txtNewOrder.getText())).build();
            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Failed to call REST api: \n" + e.toString());
            alert.showAndWait();
        }
    }

    public void btnFetchPancount_Clicked(ActionEvent actionEvent) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/pans/clean/count"))
                    .GET().build();
            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            txtPanCount.setText(response.body());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Failed to call REST api: \n" + e.toString());
            alert.showAndWait();
        }

    }

    public void btnExit_Clicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}