package com.pitroq.kevin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layouts/main-panel.fxml"));
        String css = getClass().getResource("styles/style.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().setAll(css);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("K.E.V.I.N.");
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}