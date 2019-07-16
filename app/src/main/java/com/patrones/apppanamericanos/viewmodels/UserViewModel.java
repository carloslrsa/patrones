package com.patrones.apppanamericanos.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.patrones.apppanamericanos.models.dao.design.async.IUserAsyncDAO;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

public class UserViewModel extends ViewModel {
    Observable<User> liveUser;
    IUserAsyncDAO userModel;

    public Observable<User> getUser(){
        if (liveUser == null) {
            liveUser = new Observable<>();
        }
        return liveUser;
    }

    public void login(String dni, String password, Context context){
        if(userModel == null)
            userModel = FactoryAsyncDAO.getInstance(context).getUserDAO();
        userModel.login(new User(dni, password, -1), new SimpleCallback<User>() {
            @Override
            public void OnResult(User result) {
                liveUser.notifyObservers(result);
            }

            @Override
            public void OnFailure(String message) {
                liveUser.notifyObservers(null);
            }
        });
    }
}
