package com.patrones.apppanamericanos.models.dao.design.async;
import com.patrones.apppanamericanos.models.entities.Profile;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

public interface IProfileAsyncDAO {
    void getFromUser(User user, SimpleCallback<Profile> callback);
    void update(Profile profile, SimpleCallback<Profile> callback);
    void create(Profile profile, SimpleCallback<Profile> callback);
}
