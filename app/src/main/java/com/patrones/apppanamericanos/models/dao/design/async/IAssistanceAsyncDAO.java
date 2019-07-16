package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.Assistance;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface IAssistanceAsyncDAO {
    void getFromAssistant(String assistantId, SimpleCallback<List<Assistance>> callback);
}
