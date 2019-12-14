package com.example.hallelujah.models;

import org.parceler.Parcel;

@Parcel
public class Note {
    String note;
    String index;
    String pushId;
    String title;

    public Note(String note, String title) {
        this.note = note;
        this.title = title;
        this.index = "not_specified";
    }

    public Note() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
