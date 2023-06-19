package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Config {
    private final String DIR_PATH = System.getenv("appdata") + File.separatorChar + "Kevin";
    private final String FILE_NAME = "config.json";
    private final String FILE_PATH = DIR_PATH + File.separatorChar + FILE_NAME;
    private final File file = new File(FILE_PATH);
    private Map<String, String> map;

    public Config() {
        if (!file.exists()) {
            createDirAndFile();
            fillFile();
        }
        else if (file.length() == 0) {
            fillFile();
        }
        load();
    }

    private boolean isJsonValid(String json) {
        try {
            JsonParser.parseString(json);
        }
        catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

    private void fillFile() {
        InputStream defaultConfigFileStream = getClass().getResourceAsStream("data/defaultConfig.json");
        try (FileOutputStream destinationConfigFileStream = new FileOutputStream(FILE_PATH)) {
            int ch;
            do {
                ch = defaultConfigFileStream.read();
                if (ch != -1) destinationConfigFileStream.write(ch);
            }
            while (ch != -1);
            defaultConfigFileStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void load() {
        String fileContent;

        try {
            fileContent = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (!isJsonValid(fileContent)) {
            fillFile();
            load();
            return;
            // this code reset file if have problems (f.e. lack of bracket)
            // TODO do warning with question "you have mistake in your settings. do you want to reset settings to default?"
            // TODO so create class which print excpetions
        }

        map = new Gson().fromJson(fileContent, new TypeToken<Map<String, String>>() {}.getType());
    }

    public String get(String key) {
        return map.get(key);

    }
}
