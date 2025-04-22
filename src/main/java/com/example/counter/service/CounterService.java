package com.example.counter.service;

import com.example.counter.model.CounterModel;

public interface CounterService {
    CounterModel increment();
    CounterModel decrement();
    CounterModel getCurrentValue();
} 