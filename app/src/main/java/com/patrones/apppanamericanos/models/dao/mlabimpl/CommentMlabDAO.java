package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.models.dao.design.async.ICommentAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.models.entities.Comment;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentMlabDAO implements ICommentAsyncDAO {

    Context mContext;

    public CommentMlabDAO(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void insert(Comment comment, SimpleCallback<Comment> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        RequestBody data = RequestBody.create(MediaType.parse("application/json"),CommentJsonHelper.getJsonRequestBody(comment));
        Call<ResponseBody> call = mlabAPI.postComments(mContext.getString(R.string.MLAB_API_KEY), data);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(CommentJsonHelper.getCommentFromResponseBody(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    @Override
    public void getFromEvent(String eventId, SimpleCallback<List<Comment>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> call = mlabAPI.getComments(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"codigo_evento\":\"%s\"}",eventId));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.OnResult(CommentJsonHelper.getCommentsFromResponseBody(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class CommentJsonHelper{
        static String getJsonRequestBody(Comment comment) {
            String retorno = "";
            JSONObject commentJO = new JSONObject();

            try {
                commentJO.put("dni", comment.getUserId());
                commentJO.put("nombres", comment.getUserName());
                commentJO.put("codigo_evento", comment.getEventId());
                commentJO.put("fecha", comment.getDate().getTime());
                commentJO.put("titulo", comment.getTitle());
                commentJO.put("opinion", comment.getDescription());

                retorno = commentJO.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retorno;
        }

        static Comment getCommentFromResponseBody(ResponseBody body) {
            try {
                JSONObject commentJO = new JSONObject(body.string());
                if (commentJO != null) {
                    return new Comment(
                            commentJO.getJSONObject("_id").getString("$oid"),
                            commentJO.getString("dni"),
                            commentJO.getString("nombres"),
                            commentJO.getString("codigo_evento"),
                            new Date(commentJO.getLong("fecha")),
                            commentJO.getString("titulo"),
                            commentJO.getString("opinion")
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        static List<Comment> getCommentsFromResponseBody(ResponseBody body) {
            List<Comment> comments = new ArrayList<>();
            try{
                JSONArray data = new JSONArray(body.string());
                if (data != null && data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject commentJO = data.getJSONObject(i);
                        if(commentJO != null){
                            comments.add(new Comment(
                                    commentJO.getJSONObject("_id").getString("$oid"),
                                    commentJO.getString("dni"),
                                    commentJO.getString("nombres"),
                                    commentJO.getString("codigo_evento"),
                                    new Date(commentJO.getLong("fecha")),
                                    commentJO.getString("titulo"),
                                    commentJO.getString("opinion")
                            ));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return comments;
        }
    }

}
