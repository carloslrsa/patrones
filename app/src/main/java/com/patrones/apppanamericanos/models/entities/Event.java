package com.patrones.apppanamericanos.models.entities;

import java.util.Date;

public class Event extends EventPreview {
    private String gender;
    private Place place;
    private String description;
    private String sport;
    private String discipline;

    public Event(String id, String title, String description, String gender, Place place, Date date, String sport, String discipline, String imageUrl) {
        super(id, title, date, imageUrl);
        this.gender = gender;
        this.place = place;
        this.description = description;
        this.sport = sport;
        this.discipline = discipline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

}
