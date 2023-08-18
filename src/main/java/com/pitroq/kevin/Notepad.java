package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notepad extends JsonFileManager {
    private final ObservableList<Note> notes = FXCollections.observableArrayList();
    private final Type notesType = new TypeToken<ObservableList<Note>>(){}.getType();
    private final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private int id = 0;
    private Config config = new Config();

    private final String API_URL = config.get("API_URL");
    private final String API_KEY = config.get("API_KEY");

    public Notepad() {
        super("notes.json");
    }

    public ObservableList<Note> getNotes() {
        return notes;
    }

    public void addNote(String title, String noteCotent) {
        notes.add(new Note(title, noteCotent, id));
        id++;
        saveToFile();
    }

    public void saveToFile() {
        if (!file.exists()) {
            createDirAndFile();
        }
        String json = String.valueOf(gson.toJsonTree(notes, notesType));

        saveFileContent(json);
    }

    private void fillNotes(String content) {
        Note[] fileNotes = gson.fromJson(content, Note[].class);
        for (Note fileNote : fileNotes) {
            String title = fileNote.getTitle();
            String noteContent = fileNote.getNoteContent();
            addNote(title, noteContent);
        }
    }

    public void loadNotesFromFile() {
        notes.clear();
        String content = getFileContent();
        fillNotes(content);
    }

    public Note getNote(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    public void deleteNote(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                notes.remove(note);
                break;
            }
        }
        saveToFile();
    }

    public void updateNote(int id, String title, String content) {
        for (Note note : notes) {
            if (note.getId() == id) {
                note.setTitle(title);
                note.setContent(content);
                break;
            }
        }
        saveToFile();
    }

    public void sendNotepadToDB() throws IOException {
        URL url = new URL(API_URL + "sendNotepadJSON.php");
        HttpURLConnection con= (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("charset", "utf-8");

        String urlParameters = "api_key=" + API_KEY + "&notepadJSON=" + getFileContent().replace("\\n", "{enter}");
//        System.out.println(urlParameters);
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        con.setRequestProperty("Content-Length", Integer.toString(postData.length));
        try (DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
            wr.write(postData);
        }

        con.getInputStream();
    }

    public void loadNotepadFromDB() throws IOException {
        URL url = new URL(API_URL + "getLatestNotepadJSON.php");
        HttpURLConnection con= (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("charset", "utf-8");

        String urlParameters = "api_key=" + API_KEY;
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        con.setRequestProperty("Content-Length", Integer.toString(postData.length));
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }

        try (BufferedReader br = new BufferedReader (
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());

            }
            String stringResponse = String.valueOf(response);
            stringResponse = stringResponse.replace("{enter}", "\n");
            notes.clear();
            fillNotes(stringResponse);

        }
    }
}

