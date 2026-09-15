package com.example.gestor.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public String saludo(@RequestParam(name = "nombre") String nombre) {
        return "Hola, " + nombre + ".";
    }

    @GetMapping("/saludos")
        public String buscar(
            @RequestParam(name = "estado", defaultValue = "todas") String estado,
            @RequestParam(name = "pagina", defaultValue = "1") int pagina) {

            return "Buscando saludos con estado " + estado
                + ", página " + pagina;
        }

    @GetMapping("/salutres")
    public String salutres(
        @RequestParam(name = "nombre", defaultValue = "mundo") String nombre ,
        @RequestParam(name = "edad", required = false, defaultValue = "500") Integer edad) {
        
            return "Hola2, " + nombre + " tienes " + edad + " años." ;
        }
    

    @GetMapping("/informes")
        public String informes(
                @RequestParam(name = "desde") LocalDate desde,
                @RequestParam(name = "activo", defaultValue = "true") boolean activo) {

            return "Desde " + desde + " (día " + desde.getDayOfMonth()
                    + " del mes " + desde.getMonthValue() + "), activo=" + activo;
        }
    

    
}