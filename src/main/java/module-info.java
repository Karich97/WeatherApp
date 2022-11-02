module com.example.wetherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.wetherapp to javafx.fxml;
    exports com.example.wetherapp;
}