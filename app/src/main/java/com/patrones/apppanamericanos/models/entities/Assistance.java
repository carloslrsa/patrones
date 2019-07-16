package com.patrones.apppanamericanos.models.entities;

public class Assistance {
    private String id;
    private EventPreview event;

    public Assistance(String id, EventPreview event) {
        this.id = id;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventPreview getEvent() {
        return event;
    }

    public void setEvent(EventPreview event) {
        this.event = event;
    }
}
