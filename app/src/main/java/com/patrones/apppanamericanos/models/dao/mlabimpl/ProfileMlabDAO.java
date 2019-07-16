package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.IProfileAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.AssistantProfile;
import com.patrones.apppanamericanos.models.entities.ParticipantProfile;
import com.patrones.apppanamericanos.models.entities.Profile;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMlabDAO implements IProfileAsyncDAO {

    private Context mContext;

    public ProfileMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getFromUser(User user, SimpleCallback<Profile> callback) {
        if (user.getType() == 0) {
            getAssistantProfile(user, callback);
        } else {
            getParticipantProfile(user,callback);
        }
    }
    private void getAssistantProfile(User user, SimpleCallback<Profile> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> callProfile = mlabAPI.getAssistant(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"dni\":\"%s\"}", user.getDni()),"{}");

        callProfile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(ProfileJsonConverter.getAssistantProfile(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }
    private void getParticipantProfile(User user, SimpleCallback<Profile> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> callProfile = mlabAPI.getParticipant(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"dni\":\"%s\"}", user.getDni()),"{}");

        callProfile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(ProfileJsonConverter.getParticipantProfile(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }
    @Override
    public void update(Profile profile, SimpleCallback<Profile> callback) {

    }

    @Override
    public void create(Profile profile, SimpleCallback<Profile> callback) {

    }

    static class ProfileJsonConverter{
        static ParticipantProfile getParticipantProfile(ResponseBody body){
            return null;
        }

        static AssistantProfile getAssistantProfile(ResponseBody body) {
            AssistantProfile profile = null;
            try{
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    JSONObject profileJson = data.getJSONObject(0);
                    profile = new AssistantProfile(profileJson.getString("dni"),
                            profileJson.getString("nombres"),
                            profileJson.getString("apellidos")
                    );

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return profile;
        }


    }
}
