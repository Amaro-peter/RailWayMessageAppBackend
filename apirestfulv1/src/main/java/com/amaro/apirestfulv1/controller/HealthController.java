package com.amaro.apirestfulv1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://192.168.15.15:5173", "http://localhost:5173", "http://127.0.0.1:5173", "https://vercel-message-app-front-end.vercel.app/"})
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "MessageApp API");
        
        return ResponseEntity.ok(response);
    }
}