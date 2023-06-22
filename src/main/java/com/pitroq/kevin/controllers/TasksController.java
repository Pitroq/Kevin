package com.pitroq.kevin.controllers;

import com.pitroq.kevin.TaskRow;
import com.pitroq.kevin.Tasks;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TasksController implements Initializable {
    @FXML
    private TableView<TaskRow> tableView;
    @FXML
    private TextArea taskTextArea;
    @FXML
    private TextField deadlineTimeTextField;
    @FXML
    private DatePicker deadlineDatePicker;

    private final Tasks tasks = new Tasks();

    private void fillTable() {
        ObservableList<TaskRow> taskRows = tasks.getTaskRows();
        tableView.setItems(taskRows);
    }

    private Button[] initButtons() {
        Button deleteButton = new Button("Del");
        deleteButton.setOnAction(this::deleteTask);

        Button updateButton = new Button("Upd");
        updateButton.setOnAction(this::updateTask);

        return new Button[] { deleteButton, updateButton };
    }

    private void loadTasksFromFile() {
        TaskRow[] taskFileRows = tasks.loadTasksFromFile();
        for (TaskRow taskFileRow : taskFileRows) {
            String addedDate = taskFileRow.getAddedDate();
            String taskText = taskFileRow.getTask().getText();
            String deadlineDate = taskFileRow.getDeadline().getText();


            tasks.addTask(addedDate, taskText, deadlineDate, initButtons());
            fillTable();
        }
    }

    private void deleteTask(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        tasks.deleteTask(id);
        fillTable();
    }

    private void updateTask(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();

        TextField taskUpdateField = (TextField) tableView.lookup("#taskUpdateField" + id);
        TextField deadlineUpdateField = (TextField) tableView.lookup("#deadlineUpdateField" + id);
        tasks.updateTask(id, taskUpdateField.getText(), deadlineUpdateField.getText());
        fillTable();
    }

    public void resetForm() {
        taskTextArea.clear();
        deadlineDatePicker.setValue(null);
        deadlineTimeTextField.setText(deadlineTimeTextField.getPromptText());
    }

    private boolean isDeadlineDateCorrect() {
        String deadlineTime = deadlineTimeTextField.getText();

        if (deadlineTime.isEmpty() & (deadlineDatePicker.getValue() == null)) {
            return true;
        }

        return !deadlineTime.chars().allMatch(Character::isLetter) &
                deadlineTime.length() == 5 &
                deadlineTime.contains(":") &
                (deadlineDatePicker.getValue() != null);
    }

    private boolean isFormCorrect() {
        return !taskTextArea.getText().isEmpty() & isDeadlineDateCorrect();
    }

    public void addNewTask() {
        if (!isFormCorrect()) return;

        String taskText = taskTextArea.getText();
        String addedDate = (new SimpleDateFormat("HH:mm dd.MM.YY").format(System.currentTimeMillis()));

        String deadlineDate;
        try {
            deadlineDate = deadlineTimeTextField.getText() + " " + DateTimeFormatter.ofPattern("dd.MM.YY").format(deadlineDatePicker.getValue());
        }
        catch (NullPointerException e) {
            deadlineDate = "";
        }

        tasks.addTask(addedDate, taskText, deadlineDate, initButtons());
        resetForm();
        fillTable();
    }

    private String columnNameToCamelCase(String name) { // TEST
        StringBuilder result = new StringBuilder();
        for (String word : name.toLowerCase().split(" ")) {
            result.append(word.replaceFirst(".", word.substring(0, 1).toUpperCase()));
        }
        result = new StringBuilder(result.substring(0, 1).toLowerCase() + result.substring(1));

        return result.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setSelectionModel(null);

        loadTasksFromFile();

        for (var column : tableView.getColumns()) {
            String name = columnNameToCamelCase(column.getText());
            column.setCellValueFactory(new PropertyValueFactory<>(name));
        }

        tableView.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });
    }

}
