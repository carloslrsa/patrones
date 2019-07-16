package com.patrones.apppanamericanos.models.dao.design.async;

import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

public interface IUserAsyncDAO {
    void login(User user, SimpleCallback<User> callback);
    void register(User user, SimpleCallback<User> callback);
}
