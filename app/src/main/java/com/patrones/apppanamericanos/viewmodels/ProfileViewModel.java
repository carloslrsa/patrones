package com.patrones.apppanamericanos.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.patrones.apppanamericanos.models.dao.design.async.IProfileAsyncDAO;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IAssistanceAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Assistance;
import com.patrones.apppanamericanos.models.entities.Profile;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    Observable<Profile> liveProfile;
    Observable<List<Assistance>> liveAssistances;
    IProfileAsyncDAO profileDAO;
    IAssistanceAsyncDAO assistanceDAO;

    public Observable<Profile> getProfile(){
        if (liveProfile == null) {
            liveProfile = new Observable<>();
        }
        return liveProfile;
    }

    public Observable<List<Assistance>> getAssistances(){
        if (liveAssistances == null) {
            liveAssistances = new Observable<>();
        }
        return liveAssistances;
    }

    public void loadProfile(User user, Context context){
        if(profileDAO == null)
            profileDAO = FactoryAsyncDAO.getInstance(context).getProfileDAO();
        profileDAO.getFromUser(user, new SimpleCallback<Profile>() {
            @Override
            public void OnResult(Profile result) {
                liveProfile.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                liveProfile.notifyObservers(null);
            }
        });
    }

    public void loadAssistances(User user, Context context) {
        if(assistanceDAO == null)
            assistanceDAO = FactoryAsyncDAO.getInstance(context).getAssistanceDAO();
        assistanceDAO.getFromAssistant(user.getDni(), new SimpleCallback<List<Assistance>>() {
            @Override
            public void OnResult(List<Assistance> result) {
                liveAssistances.notifyObservers(result);
            }
            @Override
            public void OnFailure(String message) {
                liveAssistances.notifyObservers(null);
            }
        });
    }
}
