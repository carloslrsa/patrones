package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.models.entities.Event;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface IEventAsyncDAO {
    void get(String id, SimpleCallback<Event> callback);
    void getPreviewFromQry(String qry, SimpleCallback<List<EventPreview>> callback);
}
