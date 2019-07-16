package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.patrones.apppanamericanos.models.dao.design.async.IDisciplineAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IEventAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IPlaceAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.ISportAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.Discipline;
import com.patrones.apppanamericanos.models.entities.Place;
import com.patrones.apppanamericanos.models.entities.Sport;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.models.entities.Event;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMlabDAO implements IEventAsyncDAO {

    private Context mContext;

    public EventMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void get(String id, SimpleCallback<Event> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getEvent(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"codigo_evento\":\"%s\"}",id));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                IPlaceAsyncDAO placeDAO = FactoryAsyncDAO.getInstance(mContext).getPlaceDAO();
                Event event = EventJsonConverter.getEvent(response.body());
                placeDAO.get(event.getPlace().getId(), new SimpleCallback<Place>() {
                    @Override
                    public void OnResult(Place result) {
                        event.setPlace(result);

                        ISportAsyncDAO sportDAO = FactoryAsyncDAO.getInstance(mContext).getSportDAO();
                        sportDAO.get(event.getSport(), new SimpleCallback<Sport>() {
                            @Override
                            public void OnResult(Sport result) {
                                event.setSport(result.getName());

                                IDisciplineAsyncDAO disciplineDAO = FactoryAsyncDAO.getInstance(mContext).getDisciplineDAO();
                                disciplineDAO.get(event.getDiscipline(), new SimpleCallback<Discipline>() {
                                    @Override
                                    public void OnResult(Discipline result) {
                                        event.setDiscipline(result.getName());


                                        callback.OnResult(event);
                                    }

                                    @Override
                                    public void OnFailure(String message) {
                                        callback.OnFailure("ERROR");
                                    }
                                });
                            }
                            @Override
                            public void OnFailure(String message) {
                                callback.OnFailure("ERROR");
                            }
                        });
                    }
                    @Override
                    public void OnFailure(String message) {
                        callback.OnFailure("ERROR");
                    }
                });
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void getPreviewFromQry(String qry, SimpleCallback<List<EventPreview>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> callPreviews = mlabAPI.getEvent(mContext.getString(R.string.MLAB_API_KEY), qry);

        callPreviews.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(EventJsonConverter.getEventPreviewList(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class EventJsonConverter{

        static List<EventPreview> getEventPreviewList(ResponseBody body){
            List<EventPreview> events = new ArrayList<>();
            try{
                JSONArray data = new JSONArray(body.string());

                for (int i = 0; i < data.length(); i++) {
                    JSONObject eventJson = data.getJSONObject(i);
                    if(eventJson != null){
                        events.add(new EventPreview(eventJson.getString("codigo_evento"),
                                eventJson.getString("nombre_evento"),
                                new Date(eventJson.getLong("fecha")),
                                eventJson.getString("imagen")
                        ));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return events;
        }

        static Event getEvent(ResponseBody body){
            Event event = null;
            try{
                JSONArray data = new JSONArray(body.string());
                JSONObject eventJson = data.getJSONObject(0);

                if(eventJson != null){
                    event = new Event(eventJson.getString("codigo_evento"),
                            eventJson.getString("nombre_evento"),
                            eventJson.getString("descripcion_evento"),
                            eventJson.getString("genero_evento"),
                            new Place(eventJson.getString("codigo_sede"),"","",new LatLng(0.0,0.0)),
                            new Date(eventJson.getLong("fecha")),
                            eventJson.getString("codigo_deporte"),
                            eventJson.getString("codigo_disciplina"),
                            eventJson.getString("imagen")
                            );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return event;
        }
    }
}
