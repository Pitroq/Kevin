package com.pitroq.kevin;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileManager {
    protected final String DIR_PATH = System.getenv("appdata") + File.separatorChar + "Kevin";
    protected String fileName;
    protected String filePath;
    protected File file;

    public JsonFileManager(String fileName) {
        this.fileName = fileName;
        filePath = DIR_PATH + File.separatorChar + fileName;
        file = new File(filePath);
    }

    public void createDirAndFile() {
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

    public boolean isJsonValid(String json) { // TEST
        try {
            JsonParser.parseString(json);
        }
        catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

    public void saveFileContent(String content) {
        try {
            Files.writeString(Path.of(filePath), content, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileContent() {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }
}
