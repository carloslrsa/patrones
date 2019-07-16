package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.IDisciplineAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.Discipline;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisciplineMlabDAO implements IDisciplineAsyncDAO {

    private Context mContext;

    public DisciplineMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void get(String id, SimpleCallback<Discipline> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getDiscipline(mContext.getString(R.string.MLAB_API_KEY),"{}");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(DisciplineJsonConverter.get(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void getFromAllSports(SimpleCallback<Map<String, List<Discipline>>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getDiscipline(mContext.getString(R.string.MLAB_API_KEY),"{}");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(DisciplineJsonConverter.getAllDisciplinesSportMap(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class DisciplineJsonConverter{
        static Discipline get(ResponseBody body) {
            Discipline discipline = null;

            try{
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    JSONObject disciplineJO = data.getJSONObject(0);
                    String sportId = disciplineJO.getString("codigo_deporte");

                    discipline = new Discipline(
                            disciplineJO.getString("codigo_disciplina"),
                            disciplineJO.getString("nombre_disciplina"),
                            sportId);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return discipline;
        }

        static Map<String,List<Discipline>> getAllDisciplinesSportMap(ResponseBody body){
            Map<String,List<Discipline>> disciplines = new HashMap<>();

            try{
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject disciplineJO = data.getJSONObject(i);
                        String sportId = disciplineJO.getString("codigo_deporte");

                        if(!disciplines.containsKey(sportId)){
                            disciplines.put(sportId,new ArrayList<>());
                        }

                        disciplines.get(sportId).add(new Discipline(
                                disciplineJO.getString("codigo_disciplina"),
                                disciplineJO.getString("nombre_disciplina"),
                                sportId));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return disciplines;
        }
    }
}
