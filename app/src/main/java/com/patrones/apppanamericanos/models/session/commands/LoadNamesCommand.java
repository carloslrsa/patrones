package com.patrones.apppanamericanos.models.session.commands;

import android.content.Context;
import android.content.SharedPreferences;

import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;

public class LoadNamesCommand implements ISessionCommand<String> {
    private Context mContext;

    public LoadNamesCommand(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String execute() {
        SharedPreferences preferences = mContext.getSharedPreferences(SessionManager.SESSIONNAME, Context.MODE_PRIVATE);
        String names = preferences.getString(SessionManager.FULLNAME, "");

        if (!names.isEmpty()) {
            return names;

        }
        return null;
    }
}
