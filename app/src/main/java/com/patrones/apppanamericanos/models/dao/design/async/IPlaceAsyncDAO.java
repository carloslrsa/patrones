package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.PlacePreview;
import com.patrones.apppanamericanos.models.entities.Place;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface IPlaceAsyncDAO {
    void get(String id, SimpleCallback<Place> callback);
    void getAllPreviews(SimpleCallback<List<PlacePreview>> callback);
}
