package com.pitroq.kevin;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LockScreen {
    private static Stage stage;
    private final Config config = new Config();
    private final int sleepTime = Integer.parseInt(config.get("lockScreenToFrontRefreshRate"));

    private static Timer timer;

    private void lockScreenToFrontSchedule() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(stage::toFront);
            }
        }, sleepTime, sleepTime);
    }

    public void createLockScreen() throws IOException {
        stage = new Stage();
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint(null);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setOnCloseRequest(Event::consume);

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("layouts/lock-screen-view.fxml")));
        scene.getStylesheets().setAll(getClass().getResource("styles/main.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
        lockScreenToFrontSchedule();
    }

    public static void closeLockScreen() {
        timer.cancel();
        stage.close();
    }
}
