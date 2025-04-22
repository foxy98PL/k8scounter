package com.example.counter.model;

public class CounterModel {
    private int value;

    public CounterModel() {
        this.value = 0;
    }

    public CounterModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }
} 