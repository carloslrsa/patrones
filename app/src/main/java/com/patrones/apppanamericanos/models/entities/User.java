package com.patrones.apppanamericanos.models.entities;

public class User {
    private String dni;
    private String password;
    private int type;

    public User(String dni, String password, int type) {
        this.dni = dni;
        this.password = password;
        this.type = type;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
