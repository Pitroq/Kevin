package com.pitroq.kevin;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TaskRow {
    public String addedDate;
    public String taskText;
    public String deadline;
    private final Button[] actions;
    public int id;

    public TaskRow(String addedDate, String taskText, String deadline, Button[] actions, int id) {
        this.addedDate = addedDate;
        this.taskText= taskText;
        this.deadline = deadline;
        this.id = id;
        this.actions = actions;
    }

    public HBox getActions() {
        HBox hbox = new HBox();
        for (Button action : actions) {
            action.setUserData(id);
            hbox.getChildren().add(action);
        }

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);

        return hbox;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public TextField getTask() {
        TextField textField = new TextField(taskText);
        textField.setId("taskUpdateField" + id);

        return textField;
    }

    public TextField getDeadline() {
        TextField textField = new TextField(deadline);
        textField.setId("deadlineUpdateField" + id);
        return textField;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }


}
