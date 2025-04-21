package com.example.counter.service;

import com.example.counter.model.Counter;

public interface CounterService {
    Counter increment();
    Counter decrement();
    Counter getCurrentValue();
} 