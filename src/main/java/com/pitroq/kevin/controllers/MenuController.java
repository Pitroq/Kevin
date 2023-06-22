package com.pitroq.kevin.controllers;

import com.pitroq.kevin.LockScreen;
import com.pitroq.kevin.Main;

import java.io.IOException;

public class MenuController {

    public void showConfigPane() {
        Main.showPane("config");
    }

    public void lockComputer() throws IOException {
        LockScreen lockScreen = new LockScreen();
        lockScreen.createLockScreen();
    }

    public void showBrowserPane() {
        Main.showPane("browser");
    }

    public void showTasksPane() {
        Main.showPane("tasks");
    }


}
