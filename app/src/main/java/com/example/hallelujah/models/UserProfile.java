package com.example.hallelujah.models;

public class UserProfile {
    String userName;
    boolean admin;
    boolean member;
    String pushId;

    public UserProfile(String userName, boolean admin, boolean member) {
        this.userName = userName;
        this.admin = admin;
        this.member = member;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }
}
