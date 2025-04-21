package com.example.counter.service;

import com.example.counter.model.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CounterServiceImplTest {

    private static final int INITIAL_VALUE = 0;
    private static final int INCREMENTED_VALUE = 1;
    private static final int DOUBLE_INCREMENTED_VALUE = 2;
    private static final int DECREMENTED_VALUE = 1;

    @InjectMocks
    private CounterServiceImpl counterService;

    @BeforeEach
    void setUp() {
        // No need to initialize as @InjectMocks handles this
    }

    @Test
    void shouldIncreaseValueByOneWhenIncrementing() {
        // Given
        // Initial state is set up in setUp()

        // When
        Counter result = counterService.increment();

        // Then
        assertEquals(INCREMENTED_VALUE, result.getValue(), 
            "Counter value should be incremented by 1 from initial value");
    }

    @Test
    void shouldDecreaseValueByOneWhenDecrementing() {
        // Given
        counterService.increment();
        counterService.increment();

        // When
        Counter result = counterService.decrement();

        // Then
        assertEquals(DECREMENTED_VALUE, result.getValue(), 
            "Counter value should be decremented by 1 from double incremented value");
    }

    @Test
    void shouldReturnCurrentValueWhenGettingValue() {
        // Given
        counterService.increment();
        counterService.increment();

        // When
        Counter result = counterService.getCurrentValue();

        // Then
        assertEquals(DOUBLE_INCREMENTED_VALUE, result.getValue(), 
            "Counter should return current value after two increments");
    }

    @Test
    void shouldReturnZeroWhenGettingInitialValue() {
        // Given
        // Initial state is set up in setUp()

        // When
        Counter result = counterService.getCurrentValue();

        // Then
        assertEquals(INITIAL_VALUE, result.getValue(), 
            "Counter should return initial value of 0");
    }
} 