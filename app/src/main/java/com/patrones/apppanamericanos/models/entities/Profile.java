package com.patrones.apppanamericanos.models.entities;

public class Profile {
    private String dni;
    private String firstname;
    private String lastname;

    public Profile(String dni, String names, String lastname) {
        this.dni = dni;
        this.firstname = names;
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
