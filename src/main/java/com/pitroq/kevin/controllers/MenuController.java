package com.pitroq.kevin.controllers;

import com.pitroq.kevin.LockScreen;
import com.pitroq.kevin.Main;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MenuController {
    public void lockComputer() throws IOException {
        LockScreen lockScreen = new LockScreen();
        lockScreen.createLockScreen();
    }

    public void showPane(MouseEvent mouseEvent) {
        String viewName = (String) ((Node) mouseEvent.getSource()).getUserData();
        Main.showPane(viewName);
    }
}
