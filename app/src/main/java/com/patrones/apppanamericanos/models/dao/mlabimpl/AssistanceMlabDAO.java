package com.patrones.apppanamericanos.models.dao.mlabimpl;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.IAssistanceAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IEventAsyncDAO;
import com.patrones.apppanamericanos.models.services.mlab.MlabAPI;
import com.patrones.apppanamericanos.models.services.ServiceGenerator;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Assistance;
import com.patrones.apppanamericanos.models.entities.EventPreview;
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

public class AssistanceMlabDAO implements IAssistanceAsyncDAO {

    Context mContext;

    public AssistanceMlabDAO(Context context) {
        mContext = context;
    }

    @Override
    public void getFromAssistant(String assistantId, SimpleCallback<List<Assistance>> callback) {
        MlabAPI mlabAPI = ServiceGenerator.createServiceScalar(MlabAPI.class);
        Call<ResponseBody> callAssistenceCodes = mlabAPI.getAssistant(mContext.getString(R.string.MLAB_API_KEY), String.format("{\"dni\":\"%s\"}", assistantId),"{\"codigos_asistencia\":1}");

        callAssistenceCodes.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String assistenceCodesJson = AssistanceJsonConverter.getAssistenceCodesJson(response.body());
                Call<ResponseBody> callAssistences = mlabAPI.getAssistanceCode(mContext.getString(R.string.MLAB_API_KEY), assistenceCodesJson);

                callAssistences.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String eventsFromAssistenceCodesJson = AssistanceJsonConverter.getEventsFromAssistenceCodesJson(response.body());
                        Map<String, String> auxiliaryAssistenceEventMap = AssistanceJsonConverter.getAuxiliaryAssistenceEventMap(response.body());
                        IEventAsyncDAO eventDAO = FactoryAsyncDAO.getInstance(mContext).getEventDAO();

                        eventDAO.getPreviewFromQry(eventsFromAssistenceCodesJson, new SimpleCallback<List<EventPreview>>() {
                            @Override
                            public void OnResult(List<EventPreview> result) {
                                List<Assistance> assistances = new ArrayList<>();
                                for (EventPreview eventPreview : result) {
                                    assistances.add(new Assistance(auxiliaryAssistenceEventMap.get(eventPreview.getId()), eventPreview));
                                }
                                callback.OnResult(assistances);
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.OnFailure("ERROR");
            }
        });
    }

    static class AssistanceJsonConverter{
        static Map<String, String> getAuxiliaryAssistenceEventMap(ResponseBody body) {
            Map<String, String> returnMap = new HashMap<>();
            try{
                JSONArray data = new JSONArray((body.string()));

                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assisntenceJO = data.getJSONObject(i);
                        String assistenceCode = assisntenceJO.getString("codigo_asistencia");
                        String eventCode = assisntenceJO.getString("codigo_evento");

                        if(!returnMap.containsKey(eventCode)){
                            returnMap.put(eventCode, assistenceCode);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnMap;
        }
        static String getAssistenceCodesJson(ResponseBody body) {
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

        static String getEventsFromAssistenceCodesJson(ResponseBody body) {
            String returnBody = "";
            try{
                JSONArray data = new JSONArray((body.string()));

                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assisntenceJO = data.getJSONObject(i);
                        returnBody += "\""+ assisntenceJO.getString("codigo_evento") + "\",";
                    }
                    returnBody = returnBody.substring(0, returnBody.length() - 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return String.format("{\"codigo_evento\":{ $in: [%s] }}",returnBody);
        }
    }
}
