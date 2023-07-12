package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notepad extends JsonFileManager {
    private final ObservableList<Note> notes = FXCollections.observableArrayList();
    private final Type notesType = new TypeToken<ObservableList<Note>>(){}.getType();
    private final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private int id = 0;

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

    public void sendNotepadToDB() {
        Database database;
        try {
            database = new Database().connect();
            String query = "INSERT INTO notepad VALUES(null, now(), '" + getFileContent() + "');";
            query = query.replace("\\n", "{enter}");
            database.sendQuery(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        database.close();
    }

    public void loadNotepadFromDB() {
        Database database;
        String databaseNotepadContent = null;
        try {
            database = new Database().connect();
            ResultSet resultSet = database.sendQueryWithResult("SELECT notepadJSON FROM notepad ORDER BY sendDate DESC LIMIT 1");
            if (resultSet.next()) {
                notes.clear();

                String result = resultSet.getString("notepadJSON");
                result = result.replace("{enter}", "\\n");
                databaseNotepadContent = result;
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fillNotes(databaseNotepadContent);
        database.close();
    }
}

