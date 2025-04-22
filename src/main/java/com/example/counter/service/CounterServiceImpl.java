package com.example.counter.service;

import com.example.counter.model.CounterModel;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {
    private CounterModel counterModel = new CounterModel();

    @Override
    public CounterModel increment() {
        counterModel.increment();
        return counterModel;
    }

    @Override
    public CounterModel decrement() {
        counterModel.decrement();
        return counterModel;
    }

    @Override
    public CounterModel getCurrentValue() {
        return counterModel;
    }
} 