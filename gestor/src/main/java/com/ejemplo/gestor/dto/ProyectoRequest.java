package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO d'Entrada per a la creació i substitució de Proyectos (POST /proyectos, PUT /proyectos/{id}).
 * 
 * Responsabilitats:
 * - Filtra només els camps permesos a l'hora de registrar un projecte.
 * - Exclou camps interns de la instància com 'id', 'numeroIncidencias' o la llista 'tareas'.
 * - Declara les restriccions de Bean Validation (Sessió 12 - Pas 7).
 */
public class ProyectoRequest {

    /** Obligatori, entre 3 i 80 caràcters. */
    @NotBlank
    @Size(min = 3, max = 80)
    private String nombre;

    /** Opcional; si s'indica, màxim 500 caràcters. */
    @Size(max = 500)
    private String descripcion;

    /** Estat inicial del projecte (opcional, per defecte true). */
    private Boolean activo;

    public ProyectoRequest() {
    }

    public ProyectoRequest(String nombre, String descripcion, Boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

