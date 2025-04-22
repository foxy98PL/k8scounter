package com.example.counter.health;

import com.example.counter.model.CounterModel;
import com.example.counter.service.CounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CounterModelHealthIndicatorTest {

    private static final String SERVICE_NAME = "counter";
    private static final String STATUS_OPERATIONAL = "operational";
    private static final String STATUS_DOWN = "down";
    private static final String ERROR_KEY = "error";
    private static final String SERVICE_KEY = "service";
    private static final String STATUS_KEY = "status";
    private static final String TEST_ERROR_MESSAGE = "Service unavailable";

    @Mock
    private CounterService counterService;

    @InjectMocks
    private CounterHealthIndicator healthIndicator;

    @BeforeEach
    void setUp() {
        // No need to initialize as @InjectMocks and @Mock handle this
    }

    @Test
    void shouldReturnUpStatusWhenServiceIsOperational() {
        // Given
        when(counterService.getCurrentValue()).thenReturn(new CounterModel(1));

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.UP, health.getStatus(), 
            "Health status should be UP when service is operational");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_OPERATIONAL, health.getDetails().get(STATUS_KEY), 
            "Status should be 'operational' in health details");
        verify(counterService).getCurrentValue();
    }

    @Test
    void shouldReturnDownStatusWhenServiceThrowsException() {
        // Given
        when(counterService.getCurrentValue()).thenThrow(new RuntimeException(TEST_ERROR_MESSAGE));

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.DOWN, health.getStatus(), 
            "Health status should be DOWN when service throws exception");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_DOWN, health.getDetails().get(STATUS_KEY), 
            "Status should be 'down' in health details");
        assertEquals(TEST_ERROR_MESSAGE, health.getDetails().get(ERROR_KEY), 
            "Error message should be included in health details");
        verify(counterService).getCurrentValue();
    }

    @Test
    void shouldReturnUpStatusWhenServiceReturnsNull() {
        // Given
        when(counterService.getCurrentValue()).thenReturn(null);

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.UP, health.getStatus(), 
            "Health status should be UP even when service returns null");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_OPERATIONAL, health.getDetails().get(STATUS_KEY), 
            "Status should be 'operational' in health details");
        verify(counterService).getCurrentValue();
    }

    @Test
    void shouldReturnUpStatusWhenServiceReturnsZero() {
        // Given
        when(counterService.getCurrentValue()).thenReturn(new CounterModel(1));

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.UP, health.getStatus(), 
            "Health status should be UP when service returns zero");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_OPERATIONAL, health.getDetails().get(STATUS_KEY), 
            "Status should be 'operational' in health details");
        verify(counterService).getCurrentValue();
    }

    @Test
    void shouldReturnDownStatusWhenServiceThrowsSpecificException() {
        // Given
        String specificErrorMessage = "Database connection failed";
        when(counterService.getCurrentValue()).thenThrow(new IllegalStateException(specificErrorMessage));

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.DOWN, health.getStatus(), 
            "Health status should be DOWN when service throws specific exception");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_DOWN, health.getDetails().get(STATUS_KEY), 
            "Status should be 'down' in health details");
        assertEquals(specificErrorMessage, health.getDetails().get(ERROR_KEY), 
            "Specific error message should be included in health details");
        verify(counterService).getCurrentValue();
    }

    @Test
    void shouldReturnUpStatusWhenServiceReturnsNegativeValue() {
        // Given
        when(counterService.getCurrentValue()).thenReturn(new CounterModel(-1));

        // When
        Health health = healthIndicator.health();

        // Then
        assertEquals(Status.UP, health.getStatus(), 
            "Health status should be UP when service returns negative value");
        assertEquals(SERVICE_NAME, health.getDetails().get(SERVICE_KEY), 
            "Service name should be 'counter' in health details");
        assertEquals(STATUS_OPERATIONAL, health.getDetails().get(STATUS_KEY), 
            "Status should be 'operational' in health details");
        verify(counterService).getCurrentValue();
    }
} 