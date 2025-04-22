package com.example.counter.health;

import com.example.counter.service.CounterService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CounterHealthIndicator implements HealthIndicator {

    private final CounterService counterService;

    public CounterHealthIndicator(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public Health health() {
        try {
            counterService.getCurrentValue();
            return Health.up()
                .withDetail("service", "counter")
                .withDetail("status", "operational")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("service", "counter")
                .withDetail("status", "down")
                .withDetail("error", e.getMessage())
                .build();
        }
    }
} 