package com.patrones.apppanamericanos.models.session.commands;

import android.content.Context;
import android.content.SharedPreferences;

import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;
import com.patrones.apppanamericanos.models.session.SessionManager;

public class InitUserCommand implements ISessionCommand<User> {
    User mUser;
    Context mContext;

    public InitUserCommand(Context context, User user) {
        mContext = context;
        mUser = user;
    }

    @Override
    public User execute() {
        SharedPreferences preferences = mContext.getSharedPreferences(SessionManager.SESSIONNAME, Context.MODE_PRIVATE);
        String dni = preferences.getString(SessionManager.DNI, "");
        if(!dni.isEmpty())
            return null;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SessionManager.DNI, mUser.getDni());
        editor.putInt(SessionManager.TYPE, mUser.getType());
        editor.commit();
        return mUser;
    }
}
