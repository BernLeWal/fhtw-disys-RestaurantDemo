module at.fhtw.disys.orderui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens at.fhtw.disys.orderui to javafx.fxml;
    exports at.fhtw.disys.orderui;
}