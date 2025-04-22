package com.example.counter.service;

import com.example.counter.model.CounterModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CounterModelServiceImplTest {

    private static final int INITIAL_VALUE = 0;
    private static final int INCREMENTED_VALUE = 1;
    private static final int DOUBLE_INCREMENTED_VALUE = 2;
    private static final int DECREMENTED_VALUE = 1;

    @InjectMocks
    private CounterServiceImpl counterService;

    @Test
    void shouldIncreaseValueByOneWhenIncrementing() {
        //Given

        // When
        CounterModel result = counterService.increment();

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
        CounterModel result = counterService.decrement();

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
        CounterModel result = counterService.getCurrentValue();

        // Then
        assertEquals(DOUBLE_INCREMENTED_VALUE, result.getValue(), 
            "Counter should return current value after two increments");
    }

    @Test
    void shouldReturnZeroWhenGettingInitialValue() {
        // Given

        // When
        CounterModel result = counterService.getCurrentValue();

        // Then
        assertEquals(INITIAL_VALUE, result.getValue(), 
            "Counter should return initial value of 0");
    }
} 