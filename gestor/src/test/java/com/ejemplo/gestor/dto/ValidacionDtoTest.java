package com.ejemplo.gestor.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // --- Sesión 12: Paso 6 - Tabla de rechazos de TareaRequest ---

    @Test
    void caso1_tareaValida_sinViolaciones() {
        TareaRequest request = new TareaRequest("Revisar el login", "alta", 7);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void caso2_tareaVacia_fallaTodo() {
        TareaRequest request = new TareaRequest();
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("prioridad")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("proyectoId")));
    }

    @Test
    void caso3_tituloVacio_fallaNotBlankYSize() {
        TareaRequest request = new TareaRequest("", "alta", 7);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
    }

    @Test
    void caso4_tituloEspacios_fallaNotBlank() {
        TareaRequest request = new TareaRequest("   ", "alta", 7);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
    }

    @Test
    void caso5_tituloDemasiadoCorto_fallaSize() {
        TareaRequest request = new TareaRequest("ab", "alta", 7);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
    }

    @Test
    void caso6_prioridadInvalida_fallaPattern() {
        TareaRequest request = new TareaRequest("Revisar", "URGENTE", 7);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("prioridad")));
    }

    @Test
    void caso7_proyectoIdNegativo_fallaPositive() {
        TareaRequest request = new TareaRequest("Revisar", "alta", -3);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("proyectoId")));
    }

    @Test
    void caso8_proyectoIdNulo_fallaNotNull() {
        TareaRequest request = new TareaRequest("Revisar", "alta", null);
        Set<ConstraintViolation<TareaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("proyectoId")));
    }

    // --- Sesión 12: Paso 7 - Reglas de ProyectoRequest y ProyectoPatchRequest ---

    @Test
    void proyectoValido_sinViolaciones() {
        ProyectoRequest request = new ProyectoRequest("Sistema Gestor", "Descripción del sistema", true);
        Set<ConstraintViolation<ProyectoRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void proyectoNombreBlanco_fallaNotBlank() {
        ProyectoRequest request = new ProyectoRequest("   ", "Descripción", true);
        Set<ConstraintViolation<ProyectoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    void proyectoNombreCorto_fallaSize() {
        ProyectoRequest request = new ProyectoRequest("ab", "Descripción", true);
        Set<ConstraintViolation<ProyectoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    void proyectoDescripcionExcesiva_fallaSize() {
        String descripcionLarga = "a".repeat(501);
        ProyectoRequest request = new ProyectoRequest("Proyecto", descripcionLarga, true);
        Set<ConstraintViolation<ProyectoRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descripcion")));
    }

    @Test
    void proyectoPatch_camposOpcionalesNull_sinViolaciones() {
        ProyectoPatchRequest request = new ProyectoPatchRequest(null, null, null);
        Set<ConstraintViolation<ProyectoPatchRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void proyectoPatch_siLlegaNombreCorto_fallaSize() {
        ProyectoPatchRequest request = new ProyectoPatchRequest("ab", null, null);
        Set<ConstraintViolation<ProyectoPatchRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }
}

