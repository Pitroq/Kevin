package com.pitroq.kevin.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesController implements Initializable {
    @FXML
    public ListView<Button> notesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notesListView.getItems().add(new Button("title"));
    }
}
