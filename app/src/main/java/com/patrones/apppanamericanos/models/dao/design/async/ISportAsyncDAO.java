package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.Sport;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface ISportAsyncDAO {
    void get(String id, SimpleCallback<Sport> callback);
    void getAll(SimpleCallback<List<Sport>> callback);
}
