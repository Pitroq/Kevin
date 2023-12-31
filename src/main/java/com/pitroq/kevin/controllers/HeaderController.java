package com.pitroq.kevin.controllers;

import com.pitroq.kevin.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Label welcomeLabel;

    private final Config config = new Config();

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> {
            Date date = new Date(System.currentTimeMillis());
            dateTimeLabel.setText(new SimpleDateFormat(config.get("dateFormat")).format(date));
        }), new KeyFrame(Duration.millis(999)));

        clock.setCycleCount(-1);
        clock.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome " + config.get("username") + "!");
        initClock();
    }
}