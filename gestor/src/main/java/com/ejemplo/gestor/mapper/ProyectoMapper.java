package com.ejemplo.gestor.mapper;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.model.Proyecto;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper per a l'entitat Proyecto (Sessió 11).
 * 
 * Responsabilitats:
 * - És l'ÚNIC punt del projecte on es tradueix entre DTOs i Models de Proyecto.
 * - Desacobla la conversió de dades de la lògica dels controladors.
 * - Centralitza la creació d'objectes nous (aModelo), transformació a resposta (aRespuesta/aRespuestas)
 *   i actualització parcial (aplicar).
 */
public class ProyectoMapper {

    private ProyectoMapper() {
    }

    /** De lo que envía el cliente a un modelo nuevo. El id lo pone quien llame. */
    public static Proyecto aModelo(ProyectoRequest peticion) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        if (peticion.getActivo() != null) {
            proyecto.setActivo(peticion.getActivo());
        } else {
            proyecto.setActivo(true);
        }
        return proyecto;
    }

    /** Del modelo a lo que publica la API. */
    public static ProyectoResponse aRespuesta(Proyecto proyecto) {
        if (proyecto == null) {
            return null;
        }
        return new ProyectoResponse(
            proyecto.getId(),
            proyecto.getNombre(),
            proyecto.getDescripcion(),
            proyecto.isActivo()
        );
    }

    public static List<ProyectoResponse> aRespuestas(List<Proyecto> proyectos) {
        List<ProyectoResponse> respuesta = new ArrayList<>();
        if (proyectos != null) {
            for (Proyecto proyecto : proyectos) {
                respuesta.add(aRespuesta(proyecto));
            }
        }
        return respuesta;
    }

    /** Aplica sobre un proyecto existente solo los campos que traiga el cambio. */
    public static void aplicar(ProyectoPatchRequest cambios, Proyecto proyecto) {
        if (cambios.getNombre() != null) {
            proyecto.setNombre(cambios.getNombre());
        }
        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }
        if (cambios.getActivo() != null) {
            proyecto.setActivo(cambios.getActivo());
        }
    }
}

