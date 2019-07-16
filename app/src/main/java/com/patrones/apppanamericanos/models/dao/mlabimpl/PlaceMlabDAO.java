package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.patrones.apppanamericanos.models.dao.design.async.IPlaceAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.PlacePreview;
import com.patrones.apppanamericanos.models.entities.Place;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceMlabDAO implements IPlaceAsyncDAO {

    private Context mContext;

    public PlaceMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void get(String id, SimpleCallback<Place> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getPlace(mContext.getString(R.string.MLAB_API_KEY),String.format("{\"codigo_sede\":\"%s\"}",id));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(PlaceJsonConverter.getPlace(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void getAllPreviews(SimpleCallback<List<PlacePreview>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getPlace(mContext.getString(R.string.MLAB_API_KEY),"{}");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(PlaceJsonConverter.getAllPlacesReview(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class PlaceJsonConverter{
        static Place getPlace(ResponseBody body){
            try{
                JSONArray data = new JSONArray(body.string());
                if(data != null && data.length() > 0){
                    JSONObject placeJson = data.getJSONObject(0);
                    if(placeJson != null){
                        return new Place(placeJson.getString("codigo_sede"),
                                placeJson.getString("nombre_sede"),
                                placeJson.getString("descripcion"),
                                new LatLng(placeJson.getDouble("latitud"), placeJson.getDouble("longitud")));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        static List<PlacePreview> getAllPlacesReview(ResponseBody body){
            List<PlacePreview> places = new ArrayList<>();
            try{
                JSONArray data = new JSONArray(body.string());

                for (int i = 0; i < data.length(); i++) {
                    JSONObject placeJson = data.getJSONObject(i);
                    if(placeJson != null){
                        places.add(new PlacePreview(placeJson.getString("codigo_sede"),
                                placeJson.getString("nombre_sede")
                        ));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return places;
        }
    }
}
