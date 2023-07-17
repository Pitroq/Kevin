package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Commands extends JsonFileManager {
    private final List<Command> commands = new ArrayList<>();
    private final Type commandsType = new TypeToken<List<Command>>(){}.getType();
    private final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private int id = 0;

    public Commands() {
        super("commands.json");
    }


    public void addCommand(String content, Button[] buttons) {
        commands.add(new Command(content, buttons, id));
        id++;
        saveToFile();
    }

    public void saveToFile() {
        if (!file.exists()) {
            createDirAndFile();
        }
        String json = String.valueOf(gson.toJsonTree(commands, commandsType));

        saveFileContent(json);
    }

    public Command getCommand(int id) {
        for (Command command : commands) {
            if (command.getId() == id) {
                return command;
            }
        }
        return null;
    }

    public void deleteCommand(int id) {
        for (Command command : commands) {
            if (command.getId() == id) {
                commands.remove(command);
                break;
            }
        }
        saveToFile();
    }

    public void updateCommand(int id, String content) {
        for (Command command : commands) {
            if (command.getId() == id) {
                command.setContent(content);
                break;
            }
        }
        saveToFile();
    }

    public Command[] loadCommandsFromFile() {
        String content = getFileContent();

        return gson.fromJson(content, Command[].class);
    }


    public List<Command> getCommands() {
        return commands;
    }
}
