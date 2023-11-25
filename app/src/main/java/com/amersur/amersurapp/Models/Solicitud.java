package com.amersur.amersurapp.Models;

public class Solicitud {

    private String uid;
    private String uiduser;
    private int uidpro;
    private String fecha;
    private int estado;

    public Solicitud() {
    }

    public Solicitud(String uid, String uiduser, int uidpro, String fecha, int estado) {
        this.uid = uid;
        this.uiduser = uiduser;
        this.uidpro = uidpro;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUiduser() {
        return uiduser;
    }

    public void setUiduser(String uiduser) {
        this.uiduser = uiduser;
    }

    public int getUidpro() {
        return uidpro;
    }

    public void setUidpro(int uidpro) {
        this.uidpro = uidpro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
