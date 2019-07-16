package com.patrones.apppanamericanos.utils.factory.dao;

import android.content.Context;

import com.patrones.apppanamericanos.models.dao.design.async.IAssistanceAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.ICommentAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IDisciplineAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IEventAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IPlaceAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IProfileAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.ISportAsyncDAO;
import com.patrones.apppanamericanos.models.dao.design.async.IUserAsyncDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.AssistanceMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.CommentMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.DisciplineMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.EventMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.PlaceMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.ProfileMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.SportMlabDAO;
import com.patrones.apppanamericanos.models.dao.mlabimpl.UserMlabDAO;

public class FactoryAsyncDAO {
    /**
     * Singleton
     */
    private static FactoryAsyncDAO instance;
    public static FactoryAsyncDAO getInstance(Context context) {
        if (instance == null) {
            instance = new FactoryAsyncDAO();
        }
        instance.setContext(context);
        return instance;
    }
    private FactoryAsyncDAO() { }
    /**
     * End Singleton
     */

    private Context mContext;

    public IAssistanceAsyncDAO getAssistanceDAO(){
        return new AssistanceMlabDAO(mContext);
    }
    public ICommentAsyncDAO getCommentaryDAO(){
        return new CommentMlabDAO(mContext);
    }
    public IDisciplineAsyncDAO getDisciplineDAO(){
        return new DisciplineMlabDAO(mContext);
    }
    public IEventAsyncDAO getEventDAO(){
        return new EventMlabDAO(mContext);
    }
    public IPlaceAsyncDAO getPlaceDAO(){
        return new PlaceMlabDAO(mContext);
    }
    public IProfileAsyncDAO getProfileDAO(){
        return new ProfileMlabDAO(mContext);
    }
    public ISportAsyncDAO getSportDAO() {
        return new SportMlabDAO(mContext);
    }
    public IUserAsyncDAO getUserDAO() {
        return new UserMlabDAO(mContext);
    }

    public Context getContext() {
        return mContext;
    }
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
