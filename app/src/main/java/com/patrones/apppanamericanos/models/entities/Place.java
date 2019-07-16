package com.patrones.apppanamericanos.models.entities;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    private PlacePreview preview;
    private String description;
    private LatLng coordinates;

    public Place(String id, String name, String description, LatLng coordinates) {
        preview = new PlacePreview(id, name);
        this.description = description;
        this.coordinates = coordinates;
    }

    public String getId() {
        return preview.getId();
    }

    public void setId(String id) {
        preview.setId(id);
    }

    public String getName() {
        return preview.getName();
    }

    public void setName(String name) {
        preview.setName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
