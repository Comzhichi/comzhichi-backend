package com.comzhichi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/api/home")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of("status", "ok", "service", "comzhichi-backend"));
    }
}