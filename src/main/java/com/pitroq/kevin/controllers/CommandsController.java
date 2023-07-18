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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CommandsController  implements Initializable {
    @FXML
    private TextField newCommandTextField;
    @FXML
    private ListView<HBox> commandsListView;

    private final Commands commands = new Commands();

    private Button[] initButtons() {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(this::deleteCommand);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(this::updateCommand);

        Button runButton = new Button("Run");
        runButton.setOnAction(this::runCommand);

        return new Button[] { deleteButton, updateButton, runButton };
    }

    @FXML
    private void updateCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();

        TextField updateTextField = (TextField) commandsListView.lookup("#commandUpdateField" + id);
        String newCommandContent = updateTextField.getText();
        commands.updateCommand(id, newCommandContent);
        fillList();
    }

    @FXML
    private void deleteCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        commands.deleteCommand(id);
        fillList();
    }

    @FXML
    private void runCommand(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        String command = commands.getCommand(id).getContent();

        try {
            Runtime.getRuntime().exec("cmd.exe /k start cmd /k " + command);
        }
        catch (IOException ignore) {}
    }

    @FXML
    private void createNewCommand() {
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
