package com.example.counter.service;

import com.example.counter.model.Counter;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {
    private Counter counter = new Counter();

    @Override
    public Counter increment() {
        counter.increment();
        return counter;
    }

    @Override
    public Counter decrement() {
        counter.decrement();
        return counter;
    }

    @Override
    public Counter getCurrentValue() {
        return counter;
    }
} 