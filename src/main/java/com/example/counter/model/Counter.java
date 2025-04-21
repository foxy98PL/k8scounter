package com.example.counter.model;

public class Counter {
    private int value;

    public Counter() {
        this.value = 0;
    }

    public Counter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }
} 