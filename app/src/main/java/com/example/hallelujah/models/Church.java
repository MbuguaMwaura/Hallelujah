package com.example.hallelujah.models;


import org.parceler.Parcel;

@Parcel
public class Church {
    String name;
    String location;
    String description;
    String pushID;
    String activities;
    int mpesa;
    String adminUid;

    public Church(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.activities = "";
        this.mpesa = mpesa;
        this.adminUid = "";
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    public Church() {}

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public int getMpesa() {
        return mpesa;
    }

    public void setMpesa(int mpesa) {
        this.mpesa = mpesa;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
