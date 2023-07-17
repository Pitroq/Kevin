package com.pitroq.kevin;

import javafx.scene.control.Button;

public class Command {
    public String content;
    public int id;
    private final Button[] buttons;

    public Command(String content, Button[] buttons, int id) {
        this.content = content;
        this.buttons = buttons;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Button[] getButtons() {
        for (Button button : buttons) {
            button.setUserData(id);
        }

        return buttons;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
