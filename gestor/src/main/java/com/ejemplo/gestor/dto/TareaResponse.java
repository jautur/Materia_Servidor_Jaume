package com.ejemplo.gestor.dto;

/**
 * DTO d'Eixida (Representació pública) per a Tareas (Sessió 10).
 * 
 * Responsabilitats:
 * - Implementat com a Java 'record' (immutable, genera automàticament constructor, getters, equals, hash i toString).
 * - Defineix exactament les dades públiques que el client pot rebre en JSON.
 * - Protegeix l'aplicació contra la fugida accidental d'informació interna o privada del model (com ara 'notaInterna').
 */
public record TareaResponse(
    int id,
    String titulo,
    String prioridad,
    boolean completada,
    int proyectoId
) {
}

