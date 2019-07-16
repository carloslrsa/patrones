package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.IUserAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
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

public class UserMlabDAO implements IUserAsyncDAO {

    private Context mContext;

    public UserMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void login(User user, SimpleCallback<User> callback) {
        MlabAPI api = ServiceGenerator.createServiceScalar(MlabAPI.class);

        Call<ResponseBody> callLogin = api.login(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"dni\":\"%s\",\"contraseña\":\"%s\"}",user.getDni(),user.getPassword()));

        callLogin.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(ProfileJsonConverter.getUser(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void register(User user, SimpleCallback<User> callback) {

    }

    static class ProfileJsonConverter{
        static User getUser(ResponseBody body){
            User user = null;
            try{
                JSONArray data = new JSONArray(body.string());
                JSONObject userJson = data.getJSONObject(0);

                if(userJson != null){
                    user = new User(userJson.getString("dni"),userJson.getString("contraseña"),userJson.getInt("tipo"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }
    }
}
