package com.ejemplo.gestor.dto;

/**
 * DTO d'Eixida (Representació pública) per a Proyectos (Sessió 10).
 * 
 * Responsabilitats:
 * - Implementat com a Java 'record' (immutable).
 * - Centralitza els camps públics que s'exposen a l'API per a un projecte.
 * - Desacopla l'esquema extern de l'entitat interna (deixant fora estructures internes com la llista mutable de tasques o el comptador d'incidències).
 */
public record ProyectoResponse(
    int id,
    String nombre,
    String descripcion,
    boolean activo
) {
}

