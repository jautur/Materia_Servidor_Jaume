package com.ejemplo.gestor.controller;

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

import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private int siguienteId = 1;
    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }

    @GetMapping
    public List<Tarea> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        if (completada == null) {
            return tareas;
        }

        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.isCompletada() == completada) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(tarea);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(tarea);
    }

    @PostMapping(value = "/proyectos/{proyectoId}/tareas", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tarea> crearEnProyecto(
            @PathVariable(name = "proyectoId") int proyectoId,
            @RequestBody Tarea nueva) {

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

        // 2. El servidor controla la identidad y la pertenencia
        nueva.setId(siguienteId++);
        nueva.setProyectoId(proyectoId);
        tareas.add(nueva);

        // 3. Dirección permanente del recurso recién creado
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/tareas/{id}").buildAndExpand(nueva.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> sustituir(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea datos) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return ResponseEntity.ok(datos);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/espejo")
    public Tarea espejo(@RequestBody Tarea tarea) {
        System.out.println("He recibido: " + tarea.getTitulo()
                + " / " + tarea.getPrioridad()
                + " / completada=" + tarea.isCompletada());
        return tarea;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }
                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }
                return ResponseEntity.ok(tarea);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }

}