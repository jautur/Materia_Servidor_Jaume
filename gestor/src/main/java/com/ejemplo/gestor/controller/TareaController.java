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

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import jakarta.validation.Valid;

/**
 * Controlador REST per a la gestió del recurs Tarea.
 * 
 * Responsabilitats:
 * - Defineix les rutes i mètodes HTTP:
 *     GET    /tareas                     -> Listat (filtrable per ?completada=true/false)
 *     GET    /tareas/{id}                -> Detall de la tasca
 *     POST   /tareas                     -> Creació general de tasca (valida amb @Valid)
 *     POST   /proyectos/{id}/tareas      -> Creació de tasca vinculada a un projecte (la URL mana sobre el cos)
 *     PUT    /tareas/{id}                -> Substitució completa de la tasca
 *     PATCH  /tareas/{id}                -> Modificació parcial de camps
 *     DELETE /tareas/{id}                -> Eliminació (204 No Content)
 * - Utilitza DTOs (TareaRequest, TareaPatchRequest, TareaResponse) i TareaMapper.
 * - Deixa la validació de format a Bean Validation (@Valid) abans d'executar els mètodes.
 */
@RestController
@RequestMapping
public class TareaController {

    private int siguienteId = 1;
    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }

    private Tarea buscar(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    @GetMapping("/tareas")
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        List<TareaResponse> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                resultado.add(TareaMapper.aRespuesta(tarea));
            }
        }
        return resultado;
    }

    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> detalle(@PathVariable(name = "id") int id) {
        Tarea tarea = buscar(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
    }

    @PostMapping(value = "/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest peticion) {
        Tarea tarea = TareaMapper.aModelo(peticion);
        tarea.setId(siguienteId++);
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(tarea));
    }

    @PostMapping(value = "/proyectos/{proyectoId}/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @Valid @RequestBody TareaRequest peticion) {

        boolean existe = false;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == proyectoId) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        Tarea nueva = TareaMapper.aModelo(peticion);
        nueva.setId(siguienteId++);
        nueva.setProyectoId(proyectoId);
        tareas.add(nueva);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tareas/{id}").buildAndExpand(nueva.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(nueva));
    }

    @PutMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> sustituir(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest datos) {
        Tarea tarea = buscar(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        tarea.setTitulo(datos.getTitulo());
        tarea.setPrioridad(datos.getPrioridad());
        if (datos.getProyectoId() != null) {
            tarea.setProyectoId(datos.getProyectoId());
        }
        return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
    }

    @PatchMapping("/tareas/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {
        Tarea tarea = buscar(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        TareaMapper.aplicar(cambios, tarea);
        return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }
}