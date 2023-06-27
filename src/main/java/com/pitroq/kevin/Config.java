package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Config extends JsonFileManager{
    private Map<String, String> map;

    public Config() {
        super("config.json");

        if (!file.exists()) {
            createDirAndFile();
            fillFileWithDefaultFile();
        }
        else if (file.length() == 0) {
            fillFileWithDefaultFile();
        }
        load();
    }

    private void fillFileWithDefaultFile() {
        InputStream defaultConfigFileStream = getClass().getResourceAsStream("data/defaultConfig.json");
        try (FileOutputStream destinationConfigFileStream = new FileOutputStream(filePath)) {
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

    private void load() {
        String fileContent = getFileContent();
        if (!isJsonValid(fileContent)) {
            fillFileWithDefaultFile();
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
