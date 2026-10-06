package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO d'Entrada per a la modificació parcial de Tareas (PATCH /tareas/{id}).
 * 
 * Responsabilitats:
 * - Tots els camps són opcionals (poden arribar nuls).
 * - No s'utilitzen anotacions com @NotNull o @NotBlank perquè fallarien si el camp s'omet.
 * - S'utilitza el tipus envoltorio Boolean per distingir entre:
 *     null  -> Camp no enviat (no es modifica)
 *     true  -> Marcar com a completada
 *     false -> Desmarcar la tasca
 * - Les anotacions @Size i @Pattern només validen si el valor no és nul.
 */
public class TareaPatchRequest {

    /** Opcional en PATCH; si s'envia, ha de complir la longitud. */
    @Size(min = 3, max = 120)
    private String titulo;

    /** Opcional en PATCH; si s'envia, ha de coincidir amb el patró. */
    @Pattern(regexp = "baja|media|alta")
    private String prioridad;

    /** Opcional; permet distingir presència de valor booleà. */
    private Boolean completada;

    public TareaPatchRequest() {
    }

    public TareaPatchRequest(String titulo, String prioridad, Boolean completada) {
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
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

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
}

