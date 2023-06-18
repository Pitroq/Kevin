package com.pitroq.kevin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LockScreenController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label infoLabel;
    private Config config = new Config();

    public void unlockPC() {
        if (passwordField.getText().equals(config.get("password"))) {
            LockScreen.closeLockScreen();
        }
        else {
            infoLabel.setText("Bad password!");
        }
    }
}
