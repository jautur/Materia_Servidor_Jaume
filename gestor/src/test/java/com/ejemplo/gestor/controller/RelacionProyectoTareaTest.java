package com.ejemplo.gestor.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RelacionProyectoTareaTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void proyectoInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/proyectos/99999/tareas"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearTareaEnProyectoDevuelve201YRelacion() throws Exception {
        mockMvc.perform(post("/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Proyecto A\",\"descripcion\":\"Desc\",\"activo\":true}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Tarea 1\",\"prioridad\":\"alta\",\"completada\":false,\"proyectoId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.proyectoId").value(1));

        mockMvc.perform(get("/proyectos/1/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].proyectoId").value(1));
    }
}
