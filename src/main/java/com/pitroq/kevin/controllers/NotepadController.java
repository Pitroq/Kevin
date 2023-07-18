package com.pitroq.kevin.controllers;

import com.pitroq.kevin.Note;
import com.pitroq.kevin.Notepad;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NotepadController implements Initializable {
    @FXML
    private ListView<Button> notesListView;
    @FXML
    private AnchorPane notePane;
    @FXML
    private TextArea noteContent;
    @FXML
    private TextField noteTitle;

    private final Notepad notepad = new Notepad();

    @FXML
    private void loadNote(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getUserData();
        Note note = notepad.getNote(id);
        notePane.setUserData(note.getId());
        noteTitle.setText(note.getTitle());
        noteContent.setText(note.getNoteContent());
        notePane.setVisible(true);
    }

    @FXML
    private void createNewNote() {
        notepad.addNote("Untitled note", "");
        fillList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notePane.setVisible(false);
        notepad.loadNotesFromFile();
        fillList();
    }

    private void fillList() {
        notesListView.getItems().clear();
        ObservableList<Note> notes = notepad.getNotes();
        for (Note note : notes) {
            Button button = new Button(note.getTitle());
            button.setUserData(note.getId());
            button.setOnAction(this::loadNote);
            notesListView.getItems().add(button);
        }
    }

    @FXML
    private void deleteNote(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getParent().getUserData();
        notepad.deleteNote(id);
        fillList();

        notePane.setVisible(false);
    }

    @FXML
    private void updateNote(ActionEvent event) {
        int id = (int) ((Node) event.getSource()).getParent().getUserData();
        String title = noteTitle.getText();
        String content = noteContent.getText();

        notepad.updateNote(id, title, content);
        fillList();
    }

    @FXML
    private void sendNotepadToDB() {
        notepad.sendNotepadToDB();
    }

    @FXML
    private void loadNotepadFromDB() {
        notepad.loadNotepadFromDB();
        fillList();
        notePane.setVisible(false);
    }
}
