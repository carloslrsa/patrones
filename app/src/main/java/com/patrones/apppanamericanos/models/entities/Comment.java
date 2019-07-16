package com.patrones.apppanamericanos.models.entities;

import java.util.Date;

public class Comment {
    String id;
    String userId;
    String userName;
    String eventId;
    Date date;
    String title;
    String description;

    public Comment(String id, String userId, String userName, String eventId, Date date, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.eventId = eventId;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
