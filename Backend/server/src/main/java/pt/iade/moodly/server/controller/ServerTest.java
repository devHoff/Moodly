package pt.iade.moodly.server.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ServerTest {
    @GetMapping("/")
    public String home() {
        return "O servidor funciona gajos!";
    }
}

