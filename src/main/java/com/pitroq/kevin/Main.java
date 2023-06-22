package com.pitroq.kevin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final BorderPane mainPane = new BorderPane();

    public static void backToMenu() {
        showPane("menu");
    }

    public static void showPane(String fileName) {
        try {
            mainPane.setCenter(FXMLLoader.load(Main.class.getResource("layouts/" + fileName + "-view.fxml")));

            if (!fileName.equals("menu")) {
                mainPane.setLeft(FXMLLoader.load(Main.class.getResource("layouts/left-bar-view.fxml")));
            }
            else {
                mainPane.setLeft(null);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("K.E.V.I.N.");
        stage.setResizable(false);

        mainPane.setTop(FXMLLoader.load(getClass().getResource("layouts/header-view.fxml")));
        mainPane.setCenter(FXMLLoader.load(getClass().getResource("layouts/menu-view.fxml")));
        mainPane.setBottom(FXMLLoader.load(getClass().getResource("layouts/footer-view.fxml")));

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("styles/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}