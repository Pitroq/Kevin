package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class Tasks extends JsonFileManager {
    private int id = 0;

    public Tasks() {
        super("tasks.json");
    }

    private final ObservableList<TaskRow> taskRows = FXCollections.observableArrayList();
    private final Type taskRowsType = new TypeToken<ObservableList<TaskRow>>(){}.getType();
    private final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();


    public ObservableList<TaskRow> getTaskRows() {
        return taskRows;
    }

    public TaskRow[] loadTasksFromFile() {
        String content = getFileContent();

        return gson.fromJson(content, TaskRow[].class);
    }

    public void addTask(String addedDate, String taskText, String deadline, Button[] actions) {
        taskRows.add(new TaskRow(addedDate, taskText, deadline, actions, id));
        id++;
        saveToFile();
    }

    public void deleteTask(int id) {
        for (TaskRow task : taskRows) {
            if (task.getId() == id) {
                taskRows.remove(task);
                break;
            }
        }
        saveToFile();
    }

    public void updateTask(int id, String taskText, String deadline) {
        for (TaskRow task : taskRows) {
            if (task.getId() == id) {
                task.setTaskText(taskText);
                task.setDeadline(deadline);
                break;
            }
        }
        saveToFile();
    }

    public void saveToFile() {
        if (!file.exists()) {
            createDirAndFile();
        }

        String json = String.valueOf(gson.toJsonTree(taskRows,taskRowsType));

        saveFileContent(json);
    }
}
