package com.malicia.mrg.assistant.photo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class BaseController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}