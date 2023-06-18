module com.pitroq.kevin {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.web;

    opens com.pitroq.kevin to javafx.fxml;
    exports com.pitroq.kevin;
}