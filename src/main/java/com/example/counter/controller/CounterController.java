package com.example.counter.controller;

import com.example.counter.model.CounterModel;
import com.example.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/counter")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @PostMapping("/increment")
    public ResponseEntity<CounterModel> increment() {
        return ResponseEntity.ok(counterService.increment());
    }

    @PostMapping("/decrement")
    public ResponseEntity<CounterModel> decrement() {
        return ResponseEntity.ok(counterService.decrement());
    }

    @GetMapping("/value")
    public ResponseEntity<CounterModel> getValue() {
        return ResponseEntity.ok(counterService.getCurrentValue());
    }
} 