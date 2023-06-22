package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tasks {
    private final String DIR_PATH = System.getenv("appdata") + File.separatorChar + "Kevin";
    private final String FILE_NAME = "tasks.json";
    private final String FILE_PATH = DIR_PATH + File.separatorChar + FILE_NAME;
    private final File file = new File(FILE_PATH);

    private int id = 0;

    private final ObservableList<TaskRow> taskRows = FXCollections.observableArrayList();

    public ObservableList<TaskRow> getTaskRows() {
        return taskRows;
    }

    public void addTask(String addedDate, String taskText, String deadline, Button[] actions) {
        taskRows.add(new TaskRow(addedDate, taskText, deadline, actions, id));
        id++;
        saveToFile();
    }

    public void deleteTask(int id) {
        taskRows.removeIf(row -> row.getId() == id);
        saveToFile();
    }

    public void updateTask(int id, String taskText, String deadline) {
        for (TaskRow task : taskRows) {
            if (task.getId() == id) {
                task.setTaskText(taskText);
                task.setDeadline(deadline);
//                break;
            }
        }
        saveToFile();
    }

    private void createDirAndFile() {
        try {
            Path dirPath = Paths.get(DIR_PATH);
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
            }
            file.createNewFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile() {
        if (!file.exists()) {
            createDirAndFile();
        }


        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();

        Type type = new TypeToken<ObservableList<TaskRow>>(){}.getType();
        String json = String.valueOf(gson.toJsonTree(taskRows,type));

//        System.out.println(json);
        try {
            Files.writeString(Path.of(FILE_PATH), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
