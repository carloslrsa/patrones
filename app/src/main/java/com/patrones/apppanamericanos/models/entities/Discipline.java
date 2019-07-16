package com.patrones.apppanamericanos.models.entities;

public class Discipline {
    private String id;
    private String name;
    private String sportId;

    public Discipline(String id, String name, String sportId) {
        this.id = id;
        this.name = name;
        this.sportId = sportId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
}
