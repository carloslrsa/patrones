package com.patrones.apppanamericanos.models.entities;

import java.util.Date;

public class EventPreview {
    private String id;
    private String title;
    private Date date;
    private String imageUrl;

    public EventPreview(String id, String title, Date date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
