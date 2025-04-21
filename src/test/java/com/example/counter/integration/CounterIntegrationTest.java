package com.example.counter.integration;

import com.example.counter.model.Counter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterIntegrationTest {

    private static final String COUNTER_VALUE_ENDPOINT = "/api/counter/value";
    private static final String COUNTER_INCREMENT_ENDPOINT = "/api/counter/increment";
    private static final String COUNTER_DECREMENT_ENDPOINT = "/api/counter/decrement";
    private static final int INITIAL_VALUE = 0;
    private static final int INCREMENTED_VALUE = 1;
    private static final int FINAL_VALUE = 0;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldPerformCompleteCounterOperations() {
        // Given
        // Initial state

        // When - Get initial value
        ResponseEntity<Counter> getResponse = restTemplate.getForEntity(COUNTER_VALUE_ENDPOINT, Counter.class);

        // Then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode(), 
            "Initial GET request should return OK status");
        assertEquals(INITIAL_VALUE, getResponse.getBody().getValue(), 
            "Counter should start with initial value of 0");

        // When - Increment
        ResponseEntity<Counter> incrementResponse = restTemplate.postForEntity(COUNTER_INCREMENT_ENDPOINT, null, Counter.class);

        // Then
        assertEquals(HttpStatus.OK, incrementResponse.getStatusCode(), 
            "Increment POST request should return OK status");
        assertEquals(INCREMENTED_VALUE, incrementResponse.getBody().getValue(), 
            "Counter value should be incremented to 1");

        // When - Decrement
        ResponseEntity<Counter> decrementResponse = restTemplate.postForEntity(COUNTER_DECREMENT_ENDPOINT, null, Counter.class);

        // Then
        assertEquals(HttpStatus.OK, decrementResponse.getStatusCode(), 
            "Decrement POST request should return OK status");
        assertEquals(FINAL_VALUE, decrementResponse.getBody().getValue(), 
            "Counter value should be decremented back to 0");

        // When - Verify final value
        ResponseEntity<Counter> finalGetResponse = restTemplate.getForEntity(COUNTER_VALUE_ENDPOINT, Counter.class);

        // Then
        assertEquals(HttpStatus.OK, finalGetResponse.getStatusCode(), 
            "Final GET request should return OK status");
        assertEquals(FINAL_VALUE, finalGetResponse.getBody().getValue(), 
            "Counter should maintain final value of 0");
    }
} 