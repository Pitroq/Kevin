package com.pitroq.kevin;

public class Note {
    public String title;
    public String noteContent;
    public int id;

    public Note(String title, String content, int id) {
        this.title = title;
        this.noteContent = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.noteContent = content;
    }
}
