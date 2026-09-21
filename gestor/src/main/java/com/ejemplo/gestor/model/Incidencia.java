package com.ejemplo.gestor.model;

public class Incidencia {

    private int id;
    private String titulo;
    private int prioridad;
    private String autor;

    public Incidencia() {
    }

    public Incidencia(int id, String titulo, int prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNivel() {
        return prioridad;
    }

    public String autor() {
        return autor;
    }

    public boolean isUrgente() {
        return prioridad >= 3;
    }

}
