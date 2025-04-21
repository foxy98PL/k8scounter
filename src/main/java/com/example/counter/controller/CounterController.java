package com.example.counter.controller;

import com.example.counter.model.Counter;
import com.example.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/counter")
public class CounterController {

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @PostMapping("/increment")
    public ResponseEntity<Counter> increment() {
        return ResponseEntity.ok(counterService.increment());
    }

    @PostMapping("/decrement")
    public ResponseEntity<Counter> decrement() {
        return ResponseEntity.ok(counterService.decrement());
    }

    @GetMapping("/value")
    public ResponseEntity<Counter> getValue() {
        return ResponseEntity.ok(counterService.getCurrentValue());
    }
} 