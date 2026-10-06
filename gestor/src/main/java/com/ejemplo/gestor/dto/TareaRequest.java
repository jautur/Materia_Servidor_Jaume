package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO d'Entrada per a la creació i substitució completa de Tareas (POST /tareas, PUT /tareas/{id}).
 * 
 * Responsabilitats:
 * - Funciona com una «llista blanca» de camps que el client té permès enviar (protecció mass-assignment).
 * - Exclou l'atribut 'id' (el genera i controla el servidor).
 * - Exclou 'completada' (per regla de negoci, tota tasca nova neix sense completar: false).
 * - Declara les restriccions de Bean Validation (Sessió 12).
 */
public class TareaRequest {

    /** Obligatori, no buit ni només espais, entre 3 i 120 caràcters. */
    @NotBlank
    @Size(min = 3, max = 120)
    private String titulo;

    /** Obligatori, només accepta un dels tres valors permesos. */
    @NotNull
    @Pattern(regexp = "baja|media|alta")
    private String prioridad;

    /** Obligatori i positiu (> 0). S'usa Integer (envoltorio) per poder validar nuls. */
    @NotNull
    @Positive
    private Integer proyectoId;

    public TareaRequest() {
    }

    public TareaRequest(String titulo, String prioridad, Integer proyectoId) {
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.proyectoId = proyectoId;
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

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }
}

