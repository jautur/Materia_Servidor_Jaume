package com.ejemplo.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Model de domini intern per a Tarea.
 * 
 * Responsabilitats:
 * - Representa l'objecte tal com es gestiona en la memòria del servidor (o en BD en futures unitats).
 * - És una classe Java tradicional i mutable (amb getters i setters).
 * - No coneix l'existència dels DTOs ni de la capa web/REST (principi de desacoblament).
 */
public class Tarea {

    private int id;
    private String titulo;
    private String prioridad;
    private boolean completada;
    private int proyectoId;

    @JsonCreator
    public Tarea() {
    }

    public Tarea(int id, String titulo, String prioridad, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }
}