package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.ISportAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.Sport;
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

public class SportMlabDAO implements ISportAsyncDAO {

    private Context mContext;

    public SportMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void get(String id, SimpleCallback<Sport> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getSport(mContext.getString(R.string.MLAB_API_KEY),String.format("{\"codigo_deporte\":\"%s\"}",id));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(SportJsonConverter.get(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void getAll(SimpleCallback<List<Sport>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getSport(mContext.getString(R.string.MLAB_API_KEY),"{}");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(SportJsonConverter.getAll(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class SportJsonConverter {
        static Sport get(ResponseBody body) {
            try {
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    JSONObject sportJO = data.getJSONObject(0);

                    return new Sport(
                            sportJO.getString("codigo_deporte"),
                            sportJO.getString("nombre_deporte"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        static List<Sport> getAll(ResponseBody body) {
            List<Sport> sports = new ArrayList<>();
            try {
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject sportJO = data.getJSONObject(i);

                        sports.add(new Sport(
                                sportJO.getString("codigo_deporte"),
                                sportJO.getString("nombre_deporte")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sports;
        }
    }
}