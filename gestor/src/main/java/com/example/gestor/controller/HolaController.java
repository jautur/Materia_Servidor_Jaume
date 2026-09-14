package com.example.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/micropicmentacion")
    public String micropicmentacion() {
        return "Hola, micropicmentacion. Te responde mi servidor.";
    }

    @GetMapping("/pisapapeles")
    public String pisapapeles() {
        return "Hola, pisapapeles. Te responde mi servidor.";
    }

    @GetMapping("/pasas")
    public String pasas() {
        return "Hola, pasas. Te responde mi servidor.";
    }

    @GetMapping("/hola")
    public String hola() {
        return "Hola, mundo. Te responde mi servidor.";
    }

    @GetMapping("/año")
    public int año() {
        return 78;
    }
}