package com.patrones.apppanamericanos.models.dao.design.async;
import com.patrones.apppanamericanos.models.entities.Discipline;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;
import java.util.Map;

public interface IDisciplineAsyncDAO {
    void get(String id, SimpleCallback<Discipline> callback);
    void getFromAllSports(SimpleCallback<Map<String, List<Discipline>>> callback);
}
