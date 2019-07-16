package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.Comment;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface ICommentAsyncDAO {
    void insert(Comment commentT, SimpleCallback<Comment> callback);
    void getFromEvent(String eventId, SimpleCallback<List<Comment>> callback);
}
