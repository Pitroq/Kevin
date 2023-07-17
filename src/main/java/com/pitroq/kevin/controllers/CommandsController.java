package com.pitroq.kevin.controllers;

import com.pitroq.kevin.Command;
import com.pitroq.kevin.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CommandsController  implements Initializable {
    public TextField newCommandTextField;
    @FXML
    private ListView<HBox> commandsListView;


    private final Commands commands = new Commands();


    private Button[] initButtons() {
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");
        Button runButton = new Button("Run");

        deleteButton.setOnAction(this::deleteCommand);
        updateButton.setOnAction(this::updateCommand);
        runButton.setOnAction(this::runCommand);

        return new Button[] { deleteButton, updateButton, runButton };
    }

    private void updateCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();

        TextField updateTextField = (TextField) commandsListView.lookup("#commandUpdateField" + id);
        String newCommandContent = updateTextField.getText();
        System.out.println(newCommandContent);
        commands.updateCommand(id, newCommandContent);
        fillList();
    }

    private void deleteCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        commands.deleteCommand(id);
        fillList();
    }

    private void runCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        String command = commands.getCommand(id).getContent();

        try {
            Runtime.getRuntime().exec("cmd /k start /SEPARATE /I " + command);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void createNewCommand() {
        if (newCommandTextField.getText().isEmpty()) {
            return;
        }

        String content = newCommandTextField.getText();
        commands.addCommand(content, initButtons());
        fillList();
        newCommandTextField.setText("");
    }

    private void fillList() {
        commandsListView.getItems().clear();
        List<Command> commands = this.commands.getCommands();
        for (Command command : commands) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10);

            TextField textField = new TextField(command.getContent());
            textField.setId("commandUpdateField" + command.getId());
            hbox.getChildren().add(textField);
            hbox.getChildren().addAll(command.getButtons());

            commandsListView.getItems().add(hbox);
        }
    }

    private void loadCommandsFromFile() {
        Command[] fileCommands = commands.loadCommandsFromFile();
        for (Command fileCommand : fileCommands) {
            String content = fileCommand.getContent();
            commands.addCommand(content, initButtons());
        }
        fillList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCommandsFromFile();
    }
}
