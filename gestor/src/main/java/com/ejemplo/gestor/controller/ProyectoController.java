package com.ejemplo.gestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos = new ArrayList<>();

    // GET /proyectos
    // GET /proyectos?estado=activo
    @GetMapping
    public List<Proyecto> lista(
            @RequestParam(name = "estado", required = false) String estado) {

        // Si no se indica estado, devuelve todos los proyectos
        if (estado == null) {
            return proyectos;
        }

        List<Proyecto> proyectosFiltrados = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {

            // Proyectos activos
            if (estado.equalsIgnoreCase("activo") && proyecto.isActivo()) {
                proyectosFiltrados.add(proyecto);
            }

            // Proyectos inactivos
            if (estado.equalsIgnoreCase("inactivo") && !proyecto.isActivo()) {
                proyectosFiltrados.add(proyecto);
            }
        }

        return proyectosFiltrados;
    }

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public Proyecto detalle(@PathVariable int id) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }

        return null;
    }

    // GET /proyectos/{id}/incidencias
    @GetMapping("/{id}/incidencias")
    public List<Tarea> incidencias(@PathVariable int id) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto.getTareas();
            }
        }

        return new ArrayList<>();
    }

    // POST /proyectos
    @PostMapping
    public Proyecto crear(@RequestBody Proyecto proyecto) {
        proyectos.add(proyecto);
        return proyecto;
    }
}
