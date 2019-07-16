package com.patrones.apppanamericanos.models.session.commands;

import android.content.Context;
import android.content.SharedPreferences;

import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;

public class EndUserCommand implements ISessionCommand<User> {
    Context mContext;

    public EndUserCommand(Context context) {
        mContext = context;
    }

    @Override
    public User execute() {
        SharedPreferences preferences = mContext.getSharedPreferences(SessionManager.SESSIONNAME, Context.MODE_PRIVATE);
        String dni = preferences.getString(SessionManager.DNI, "");
        if(dni.isEmpty())
            return null;

        int type = preferences.getInt(SessionManager.TYPE,-1);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SessionManager.DNI);
        editor.remove(SessionManager.TYPE);
        editor.commit();

        return new User(dni, "", type);
    }
}
