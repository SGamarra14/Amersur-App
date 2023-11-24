package com.amersur.amersurapp.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String UID;
    private String CORREO;
    private String PASSWORD;
    private String APELLIDOS;
    private String TELEFONO;
    private String NOMBRES;

    public User() {
    }

    public User(String UID, String CORREO, String PASSWORD, String APELLIDOS, String TELEFONO, String NOMBRES) {
        this.UID = UID;
        this.CORREO = CORREO;
        this.PASSWORD = PASSWORD;
        this.APELLIDOS = APELLIDOS;
        this.TELEFONO = TELEFONO;
        this.NOMBRES = NOMBRES;
    }

    // Métodos getter y setter para acceder a los atributos

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }
}
