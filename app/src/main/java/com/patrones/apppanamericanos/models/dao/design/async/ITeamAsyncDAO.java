package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.Team;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public interface ITeamAsyncDAO {
    public void getFromQuery(String qry, SimpleCallback<List<Team>> callback);
}
