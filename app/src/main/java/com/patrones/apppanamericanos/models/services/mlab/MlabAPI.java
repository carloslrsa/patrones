package com.patrones.apppanamericanos.models.services.mlab;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MlabAPI {
    @GET("/api/1/databases/juegos_panamericanos/collections/usuarios")
    public Call<ResponseBody> login(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/eventos")
    public Call<ResponseBody> getEvent(@Query("apiKey") String apiKey, @Query("q") String query);

    //Nuevos

    @GET("/api/1/databases/juegos_panamericanos/collections/asistentes")
    public Call<ResponseBody> getAssistant(@Query("apiKey") String apiKey, @Query("q") String query, @Query("f") String fields);

    @GET("/api/1/databases/juegos_panamericanos/collections/participantes")
    public Call<ResponseBody> getParticipant(@Query("apiKey") String apiKey, @Query("q") String query, @Query("f") String fields);

    @GET("/api/1/databases/juegos_panamericanos/collections/equipos")
    public Call<ResponseBody> getTeam(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/codigo_asistencia")
    public Call<ResponseBody> getAssistanceCode(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/sedes")
    public Call<ResponseBody> getPlace(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/deportes")
    public Call<ResponseBody> getSport(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/disciplinas")
    public Call<ResponseBody> getDiscipline(@Query("apiKey") String apiKey, @Query("q") String query);

    @GET("/api/1/databases/juegos_panamericanos/collections/comentarios")
    public Call<ResponseBody> getComments(@Query("apiKey") String apiKey, @Query("q") String query);


    //POSTS

    @POST("/api/1/databases/juegos_panamericanos/collections/comentarios")
    public Call<ResponseBody> postComments(@Query("apiKey") String apiKey, @Body RequestBody body);

}
