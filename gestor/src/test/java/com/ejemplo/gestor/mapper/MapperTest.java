package com.ejemplo.gestor.mapper;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    // --- Sesión 11: Mapeo de Tareas ---

    @Test
    void tareaMapper_aModelo_asignaCamposYNaceSinCompletar() {
        TareaRequest request = new TareaRequest("Revisar el login", "alta", 7);
        Tarea tarea = TareaMapper.aModelo(request);

        assertEquals("Revisar el login", tarea.getTitulo());
        assertEquals("alta", tarea.getPrioridad());
        assertEquals(7, tarea.getProyectoId());
        assertFalse(tarea.isCompletada(), "Una tarea recién creada nace sin completar (false)");
        assertEquals(0, tarea.getId(), "El ID lo debe asignar el servidor posteriormente");
    }

    @Test
    void tareaMapper_aRespuesta_copiaCampos() {
        Tarea tarea = new Tarea(1, "Revisar el login", "alta", false);
        tarea.setProyectoId(7);

        TareaResponse response = TareaMapper.aRespuesta(tarea);
        assertEquals(1, response.id());
        assertEquals("Revisar el login", response.titulo());
        assertEquals("alta", response.prioridad());
        assertFalse(response.completada());
        assertEquals(7, response.proyectoId());
    }

    @Test
    void tareaMapper_aplicarPatch_soloModificaCamposPresentes() {
        Tarea tarea = new Tarea(1, "Original", "baja", false);
        tarea.setProyectoId(5);

        // Modificamos solo prioridad y completada, titulo null no debe tocar el original
        TareaPatchRequest cambios = new TareaPatchRequest(null, "alta", true);
        TareaMapper.aplicar(cambios, tarea);

        assertEquals("Original", tarea.getTitulo());
        assertEquals("alta", tarea.getPrioridad());
        assertTrue(tarea.isCompletada());
        assertEquals(5, tarea.getProyectoId());
    }

    // --- Sesión 11: Mapeo de Proyectos ---

    @Test
    void proyectoMapper_aModelo_asignaCampos() {
        ProyectoRequest request = new ProyectoRequest("Sistema Gestor", "Descripción", true);
        Proyecto proyecto = ProyectoMapper.aModelo(request);

        assertEquals("Sistema Gestor", proyecto.getNombre());
        assertEquals("Descripción", proyecto.getDescripcion());
        assertTrue(proyecto.isActivo());
        assertEquals(0, proyecto.getId());
    }

    @Test
    void proyectoMapper_aRespuesta_copiaCampos() {
        Proyecto proyecto = new Proyecto(10, "Sistema Gestor", "Descripción", true, 0);
        ProyectoResponse response = ProyectoMapper.aRespuesta(proyecto);

        assertEquals(10, response.id());
        assertEquals("Sistema Gestor", response.nombre());
        assertEquals("Descripción", response.descripcion());
        assertTrue(response.activo());
    }

    @Test
    void proyectoMapper_aplicarPatch_soloModificaCamposPresentes() {
        Proyecto proyecto = new Proyecto(10, "Nombre Original", "Desc Original", true, 0);

        ProyectoPatchRequest cambios = new ProyectoPatchRequest("Nuevo Nombre", null, false);
        ProyectoMapper.aplicar(cambios, proyecto);

        assertEquals("Nuevo Nombre", proyecto.getNombre());
        assertEquals("Desc Original", proyecto.getDescripcion());
        assertFalse(proyecto.isActivo());
    }

    // --- Sesión 11: Paso 8 - Prueba de ida y vuelta ---

    @Test
    void pruebaIdaYVuelta_tareaSobreviveAlViaje() {
        TareaRequest entrada = new TareaRequest("Revisar el login", "alta", 7);
        Tarea modelo = TareaMapper.aModelo(entrada);
        modelo.setId(42); // Simulamos la asignación del ID por el servidor
        TareaResponse salida = TareaMapper.aRespuesta(modelo);

        assertEquals(entrada.getTitulo(), salida.titulo());
        assertEquals(entrada.getPrioridad(), salida.prioridad());
        assertEquals(entrada.getProyectoId(), salida.proyectoId());
        assertEquals(42, salida.id());
        assertFalse(salida.completada());
    }
}

