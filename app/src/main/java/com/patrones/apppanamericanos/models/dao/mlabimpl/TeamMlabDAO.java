package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.models.dao.design.async.ITeamAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Team;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamMlabDAO implements ITeamAsyncDAO {
    private Context mContext;

    public TeamMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getFromQuery(String qry, SimpleCallback<List<Team>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getTeam(mContext.getString(R.string.MLAB_API_KEY), qry);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    static class TeamJsonHelper{

        static String getParticipantsQuery(ResponseBody body) {
            String returnBody = "";
            try{
                JSONArray data = new JSONArray((body.string()));
                JSONObject codeJO = data.getJSONObject(0);

                if (codeJO != null) {
                    JSONArray codeJA = codeJO.getJSONArray("codigos_asistencia");
                    for (int i = 0; i < codeJA.length(); i++) {
                        returnBody += "\""+ codeJA.get(i).toString() + "\",";
                    }
                    returnBody = returnBody.substring(0, returnBody.length() - 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return String.format("{\"codigo_asistencia\":{ $in: [%s] }}",returnBody);
        }
    }
}
