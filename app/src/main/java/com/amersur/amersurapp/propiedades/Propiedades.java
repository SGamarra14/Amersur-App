package com.amersur.amersurapp.propiedades;

public class Propiedades {

    String AREA;
    String DESCRIPCION;
    int ESTADO;
    String FOTO1;
    String FOTO2;
    String FOTO3;
    Double PRECIO;
    int TIPO;
    String UBICACION;
    int VISUALIZACIONES;
    String NOMBRE;

    int ID;

    public Propiedades() {
    }

    /*public Propiedades(String area, String descripcion, int estado, String foto1, String foto2, String foto3, Double precio, int tipo, String ubicacion, int visualizaciones, String nombre) {
        this.area = area;
        this.descripcion = descripcion;
        this.estado = estado;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.precio = precio;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.visualizaciones = visualizaciones;
        this.nombre = nombre;
    }*/

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getFOTO1() {
        return FOTO1;
    }

    public void setFOTO1(String FOTO1) {
        this.FOTO1 = FOTO1;
    }

    public String getFOTO2() {
        return FOTO2;
    }

    public void setFOTO2(String FOTO2) {
        this.FOTO2 = FOTO2;
    }

    public String getFOTO3() {
        return FOTO3;
    }

    public void setFOTO3(String FOTO3) {
        this.FOTO3 = FOTO3;
    }

    public Double getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(Double PRECIO) {
        this.PRECIO = PRECIO;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
    }

    public String getUBICACION() {
        return UBICACION;
    }

    public void setUBICACION(String UBICACION) {
        this.UBICACION = UBICACION;
    }

    public int getVISUALIZACIONES() {
        return VISUALIZACIONES;
    }

    public void setVISUALIZACIONES(int VISUALIZACIONES) {
        this.VISUALIZACIONES = VISUALIZACIONES;
    }
}
