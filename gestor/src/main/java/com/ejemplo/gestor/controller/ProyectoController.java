package com.ejemplo.gestor.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.mapper.ProyectoMapper;
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import jakarta.validation.Valid;

/**
 * Controlador REST per a la gestió del recurs Proyecto.
 * 
 * Responsabilitats:
 * - Defineix les rutes i mètodes HTTP:
 *     GET    /proyectos            -> Listat de projectes (filtrable per ?activo=true/false)
 *     GET    /proyectos/{id}       -> Detall d'un projecte
 *     GET    /proyectos/{id}/tareas-> Consulta de les tasques associades a un projecte
 *     POST   /proyectos            -> Creació de nou projecte (valida amb @Valid)
 *     PUT    /proyectos/{id}       -> Actualització completa
 *     PATCH  /proyectos/{id}       -> Modificació parcial
 *     DELETE /proyectos/{id}       -> Eliminació (204 No Content)
 * - Utilitza DTOs (ProyectoRequest, ProyectoPatchRequest, ProyectoResponse) i ProyectoMapper.
 * - Centralitza la cerca d'existència mitjançant el mètode privat buscar(id).
 */
@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private int siguienteId = 1;
    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
        this.tareas = memoria.getTareas();
    }

    private Proyecto buscar(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }

    @GetMapping
    public List<ProyectoResponse> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {
        List<ProyectoResponse> resultado = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (activo == null || proyecto.isActivo() == activo) {
                resultado.add(ProyectoMapper.aRespuesta(proyecto));
            }
        }
        return resultado;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> detalle(@PathVariable(name = "id") int id) {
        Proyecto proyecto = buscar(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaResponse>> tareasDelProyecto(
            @PathVariable(name = "id") int id) {
        if (buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }

        List<TareaResponse> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(TareaMapper.aRespuesta(tarea));
            }
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest peticion) {
        Proyecto proyecto = ProyectoMapper.aModelo(peticion);
        proyecto.setId(siguienteId++);
        proyectos.add(proyecto);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proyecto.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(ProyectoMapper.aRespuesta(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest datos) {
        Proyecto proyecto = buscar(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        proyecto.setNombre(datos.getNombre());
        proyecto.setDescripcion(datos.getDescripcion());
        if (datos.getActivo() != null) {
            proyecto.setActivo(datos.getActivo());
        }
        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProyectoResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {
        Proyecto proyecto = buscar(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }
        ProyectoMapper.aplicar(cambios, proyecto);
        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
        return ResponseEntity.noContent().build();
    }
}
