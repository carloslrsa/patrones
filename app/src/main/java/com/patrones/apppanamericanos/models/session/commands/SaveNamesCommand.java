package com.patrones.apppanamericanos.models.session.commands;

import android.content.Context;
import android.content.SharedPreferences;

import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;

public class SaveNamesCommand implements ISessionCommand<String> {

    Context mContext;
    String fullname;

    public SaveNamesCommand(Context mContext, String fullname) {
        this.mContext = mContext;
        this.fullname = fullname;
    }

    @Override
    public String execute() {
        SharedPreferences preferences = mContext.getSharedPreferences(SessionManager.SESSIONNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SessionManager.FULLNAME, fullname);
        editor.commit();
        return fullname;
    }
}
