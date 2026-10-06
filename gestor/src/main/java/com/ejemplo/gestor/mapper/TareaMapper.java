package com.ejemplo.gestor.mapper;

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.model.Tarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper per a l'entitat Tarea (Sessió 11).
 * 
 * Responsabilitats:
 * - És l'ÚNIC punt del projecte on es tradueix entre DTOs i Models de Tarea.
 * - Ni valida, ni guarda en memòria, ni atén rutes HTTP: la seua única funció és traduir.
 * - Posseeix constructor privat perquè és una classe d'utilitat amb mètodes estàtics.
 * - Manté el model lliure de dependències de l'API i els DTOs lliures de dependències del model.
 */
public class TareaMapper {

    private TareaMapper() {
    }

    /** De lo que envía el cliente a un modelo nuevo. El id lo pone quien llame. */
    public static Tarea aModelo(TareaRequest peticion) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        if (peticion.getProyectoId() != null) {
            tarea.setProyectoId(peticion.getProyectoId());
        }
        tarea.setCompletada(false);
        return tarea;
    }

    /** Del modelo a lo que publica la API. */
    public static TareaResponse aRespuesta(Tarea tarea) {
        if (tarea == null) {
            return null;
        }
        return new TareaResponse(
            tarea.getId(),
            tarea.getTitulo(),
            tarea.getPrioridad(),
            tarea.isCompletada(),
            tarea.getProyectoId()
        );
    }

    public static List<TareaResponse> aRespuestas(List<Tarea> tareas) {
        List<TareaResponse> respuesta = new ArrayList<>();
        if (tareas != null) {
            for (Tarea tarea : tareas) {
                respuesta.add(aRespuesta(tarea));
            }
        }
        return respuesta;
    }

    /** Aplica sobre una tarea existente solo los campos que traiga el cambio. */
    public static void aplicar(TareaPatchRequest cambios, Tarea tarea) {
        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }
        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }
        if (cambios.getCompletada() != null) {
            tarea.setCompletada(cambios.getCompletada());
        }
    }
}

