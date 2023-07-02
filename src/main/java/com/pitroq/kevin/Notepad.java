package com.pitroq.kevin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

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

    private void saveToFile() {
        if (!file.exists()) {
            createDirAndFile();
        }
        String json = String.valueOf(gson.toJsonTree(notes, notesType));

        saveFileContent(json);
    }

    public void loadNotesFromFile() {
        String content = getFileContent();
        Note[] fileNotes = gson.fromJson(content, Note[].class);
        for (Note fileNote : fileNotes) {
            String title = fileNote.getTitle();
            String noteContent = fileNote.getNoteContent();
            addNote(title, noteContent);
        }
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
}

