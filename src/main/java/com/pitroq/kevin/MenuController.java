package com.pitroq.kevin;

import java.io.IOException;

public class MenuController {

    public void showConfigPane() {
        Main.showPane("config");
    }

    public void lockComputer() throws IOException {
        System.out.println("locking");
        LockScreen lockScreen = new LockScreen();
        lockScreen.createLockScreen();
    }

    public void showBrowserPane() {
        Main.showPane("browser");
    }


}
