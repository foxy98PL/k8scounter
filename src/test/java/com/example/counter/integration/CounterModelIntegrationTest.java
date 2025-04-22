package com.example.counter.integration;

import com.example.counter.model.CounterModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterModelIntegrationTest {

    private static final String COUNTER_VALUE_ENDPOINT = "/api/counter/value";
    private static final String COUNTER_INCREMENT_ENDPOINT = "/api/counter/increment";
    private static final String COUNTER_DECREMENT_ENDPOINT = "/api/counter/decrement";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnInitialCounterValue() {
        // Given

        // When
        ResponseEntity<CounterModel> response = restTemplate.getForEntity(COUNTER_VALUE_ENDPOINT, CounterModel.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "GET /value should return status OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(0, response.getBody().getValue(), "Initial counter value should be 0");
    }

    @Test
    void shouldIncrementCounterValue() {
        // Given

        // When
        restTemplate.postForEntity(COUNTER_INCREMENT_ENDPOINT, null, CounterModel.class);
        ResponseEntity<CounterModel> response = restTemplate.getForEntity(COUNTER_VALUE_ENDPOINT, CounterModel.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "GET after increment should return OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().getValue(), "Counter value should be 1 after one increment");
    }

    @Test
    void shouldDecrementCounterValue() {
        // Given
        restTemplate.postForEntity(COUNTER_INCREMENT_ENDPOINT, null, CounterModel.class); // bring to 1

        // When
        restTemplate.postForEntity(COUNTER_DECREMENT_ENDPOINT, null, CounterModel.class); // back to 0
        ResponseEntity<CounterModel> response = restTemplate.getForEntity(COUNTER_VALUE_ENDPOINT, CounterModel.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "GET after decrement should return OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(0, response.getBody().getValue(), "Counter value should return to 0 after increment then decrement");
    }
}
