package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO d'Entrada per a la modificació parcial de Proyectos (PATCH /proyectos/{id}).
 * 
 * Responsabilitats:
 * - Tots els camps són opcionals (no porten @NotNull ni @NotBlank).
 * - Només s'actualitzaran els camps no nuls rebuts a la petició.
 * - S'aplica @Size per garantir que, en cas d'arribar valor, aquest compleix la longitud permesa.
 */
public class ProyectoPatchRequest {

    /** Opcional en PATCH; si arriba, ha de tenir entre 3 i 80 caràcters. */
    @Size(min = 3, max = 80)
    private String nombre;

    /** Opcional en PATCH; si arriba, màxim 500 caràcters. */
    @Size(max = 500)
    private String descripcion;

    /** Opcional; permet activar o desactivar el projecte de manera aïllada. */
    private Boolean activo;

    public ProyectoPatchRequest() {
    }

    public ProyectoPatchRequest(String nombre, String descripcion, Boolean activo) {
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

