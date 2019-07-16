package com.patrones.apppanamericanos.models.session.commands;

import android.content.Context;
import android.content.SharedPreferences;

import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;

public class LoadUserCommand implements ISessionCommand<User> {

    Context mContext;

    public LoadUserCommand(Context context) {
        mContext = context;
    }

    @Override
    public User execute() {
        SharedPreferences preferences = mContext.getSharedPreferences(SessionManager.SESSIONNAME, Context.MODE_PRIVATE);
        String dni = preferences.getString(SessionManager.DNI, "");
        int type = preferences.getInt(SessionManager.TYPE,-1);

        if (type >= 0 && !dni.isEmpty()) {
            return new User(dni, "", type);

        }
        return null;
    }
}
