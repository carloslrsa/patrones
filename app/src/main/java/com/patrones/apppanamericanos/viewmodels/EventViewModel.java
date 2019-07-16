package com.patrones.apppanamericanos.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.patrones.apppanamericanos.models.dao.design.async.ICommentAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IEventAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Comment;
import com.patrones.apppanamericanos.models.entities.Event;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public class EventViewModel extends ViewModel {
    Observable<Event> observableEvent;
    Observable<List<Comment>> observableComments;
    IEventAsyncDAO eventDAO;
    ICommentAsyncDAO commentDAO;

    public Observable<Event> getEvent() {
        if (observableEvent == null) {
            observableEvent = new Observable<>();
        }
        return  observableEvent;
    }

    public Observable<List<Comment>> getComments() {
        if (observableComments == null) {
            observableComments = new Observable<>();
        }
        return observableComments;
    }

    public void loadEvent(String id, Context context){
        if (observableEvent == null) {
            observableEvent = new Observable<>();
        }
        if (observableComments == null) {
            observableComments = new Observable<>();
        }
        if (eventDAO == null) {
            eventDAO = FactoryAsyncDAO.getInstance(context).getEventDAO();
        }
        if (commentDAO == null) {
            commentDAO = FactoryAsyncDAO.getInstance(context).getCommentaryDAO();
        }
        eventDAO.get(id, new SimpleCallback<Event>() {
            @Override
            public void OnResult(Event result) {
                observableEvent.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                observableEvent.notifyObservers(null);
            }
        });
        commentDAO.getFromEvent(id, new SimpleCallback<List<Comment>>() {
            @Override
            public void OnResult(List<Comment> result) {
                observableComments.notifyObservers(result);
            }

            @Override
            public void OnFailure(String message) {
                observableComments.notifyObservers(null);
            }
        });

    }
}
