package com.ejemplo.gestor.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Proyecto {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private int numeroIncidencias;
    private List<Tarea> tareas;

    @JsonCreator
    public Proyecto() {
    }

    public Proyecto(int id, String nombre, String descripcion, boolean activo, int numeroIncidencias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.numeroIncidencias = numeroIncidencias;
        this.tareas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getNumeroIncidencias() {
        return numeroIncidencias;
    }

    public void setNumeroIncidencias(int numeroIncidencias) {
        this.numeroIncidencias = numeroIncidencias;
    }

    public List<Tarea> getTareas() {
        if (tareas == null) {
            tareas = new ArrayList<>();
        }
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}
