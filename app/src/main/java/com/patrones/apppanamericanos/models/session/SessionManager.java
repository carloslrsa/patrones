package com.patrones.apppanamericanos.models.session;

import com.patrones.apppanamericanos.models.session.commands.design.ISessionCommand;

public class SessionManager {
    //Singleton
    private static SessionManager instance;
    public static SessionManager getInstance(){
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    private SessionManager(){
    }
    //EndSingleton

    public static String SESSIONNAME = "m_session";
    public static String DNI = "dni";
    public static String FULLNAME = "fullname";
    public static String TYPE = "type";

    public <T> T executeCommand(ISessionCommand<T> command){
        return command.execute();
    }




}
