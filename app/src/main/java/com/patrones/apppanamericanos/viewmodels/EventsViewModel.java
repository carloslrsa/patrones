package com.patrones.apppanamericanos.viewmodels;

import android.content.Context;
import androidx.lifecycle.ViewModel;

import com.patrones.apppanamericanos.models.dao.design.async.IDisciplineAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IEventAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IPlaceAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.ISportAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Discipline;
import com.patrones.apppanamericanos.models.entities.PlacePreview;
import com.patrones.apppanamericanos.models.entities.Sport;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.utils.criteria.events.CriteriaEventDate;
import com.patrones.apppanamericanos.utils.criteria.events.CriteriaEventKeyword;
import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;
import java.util.Map;

public class EventsViewModel extends ViewModel {

    Observable<List<Sport>> sports;
    Observable<Map<String, List<Discipline>>> disciplines;
    Observable<List<PlacePreview>> places;
    Observable<List<EventPreview>> events;

    IEventAsyncDAO eventModel;
    ISportAsyncDAO sportModel;
    IPlaceAsyncDAO placeModel;
    IDisciplineAsyncDAO disciplineModel;

    public Observable<List<EventPreview>> getEvents() {
        if (events == null) {
            events = new Observable<>();
        }
        return events;
    }

    public Observable<List<Sport>> getSports() {
        if (sports == null) {
            sports = new Observable<>();
        }
        return sports;
    }

    public Observable<List<PlacePreview>> getPlaces() {
        if (places == null) {
            places = new Observable<>();
        }
        return places;
    }

    public Observable<Map<String, List<Discipline>>> getDisciplines() {
        if (disciplines == null) {
            disciplines = new Observable<>();
        }
        return disciplines;
    }

    public void SearchEvents(String keyword, IDateComparatorStrategy dateStrategy, String place, String sport, String discipline, Context context) {
        String query = "";
        String sportId = "";
        String disciplineId = "";
        String placeId = "";

        for (Sport sportTO : sports.getData()) {
            if (sportTO.getName().equals(sport)) {
                sportId = sportTO.getId();
                break;
            }
        }

        if (!sportId.isEmpty() && disciplines.getData().containsKey(sportId)) {
            for (Discipline disciplineTO : disciplines.getData().get(sportId)) {
                if (disciplineTO.getName().equals(discipline)) {
                    disciplineId = disciplineTO.getId();
                    break;
                }
            }
        }

        for (PlacePreview placeTO : places.getData()) {
            if (placeTO.getName().equals(place)) {
                placeId = placeTO.getId();
                break;
            }
        }

        if (!sportId.isEmpty()) {
            if (disciplineId.isEmpty()) {
                query = String.format("{\"codigo_deporte\":\"%s\"", sportId);
            } else {
                query = String.format("{\"codigo_deporte\":\"%s\",\"codigo_disciplina\":\"%s\"", sportId, disciplineId);
            }

            if (placeId.isEmpty()) {
                query += "}";
            } else {
                query += String.format(",\"codigo_sede\":\"%s\"}");
            }
        } else {
            if (placeId.isEmpty()) {
                query += "{}";
            } else {
                query = String.format("{\"codigo_sede\":\"%s\"}",placeId);
            }
        }

        if(eventModel == null)
            eventModel = FactoryAsyncDAO.getInstance(context).getEventDAO();
        eventModel.getPreviewFromQry(query, new SimpleCallback<List<EventPreview>>() {
            @Override
            public void OnResult(List<EventPreview> result) {
                result = new CriteriaEventDate(dateStrategy).meetCriteria(result);
                result = new CriteriaEventKeyword(keyword).meetCriteria(result);
                events.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                events.notifyObservers(null);
            }
        });
    }

    public void SearchDisciplines(Context context) {
        if(sportModel == null)
            sportModel = FactoryAsyncDAO.getInstance(context).getSportDAO();
        sportModel.getAll(new SimpleCallback<List<Sport>>() {
            @Override
            public void OnResult(List<Sport> result) {
                sports.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                sports.notifyObservers(null);
            }
        });
    }

    public void SearchSports(Context context) {
        if(disciplineModel == null)
            disciplineModel = FactoryAsyncDAO.getInstance(context).getDisciplineDAO();
        disciplineModel.getFromAllSports(new SimpleCallback<Map<String, List<Discipline>>>() {
            @Override
            public void OnResult(Map<String, List<Discipline>> result) {
                disciplines.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                disciplines.notifyObservers(null);
            }
        });
    }

    public void SearchPlaces(Context context) {
        if(placeModel == null)
            placeModel = FactoryAsyncDAO.getInstance(context).getPlaceDAO();
        placeModel.getAllPreviews(new SimpleCallback<List<PlacePreview>>() {
            @Override
            public void OnResult(List<PlacePreview> result) {
                places.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                places.notifyObservers(null);
            }
        });
    }
}
