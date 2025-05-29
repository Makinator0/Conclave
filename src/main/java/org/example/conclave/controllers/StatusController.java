package org.example.conclave.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatusController {
    @GetMapping("/")
    public String getStatus() {
        return "server-status"; // Имя HTML-шаблона (без расширения)
    }
}
