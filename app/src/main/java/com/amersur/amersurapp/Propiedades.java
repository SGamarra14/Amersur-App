package com.amersur.amersurapp;

public class Propiedades {

    String area;
    String descripcion;
    int estado;
    String foto1;
    String foto2;
    String foto3;
    Double precio;
    int tipo;
    String ubicacion;
    int visualizaciones;

    public Propiedades() {
    }

    public Propiedades(String area, String descripcion, int estado, String foto1, String foto2, String foto3, Double precio, int tipo, String ubicacion, int visualizaciones) {
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
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getVisualizaciones() {
        return visualizaciones;
    }

    public void setVisualizaciones(int visualizaciones) {
        this.visualizaciones = visualizaciones;
    }
}
