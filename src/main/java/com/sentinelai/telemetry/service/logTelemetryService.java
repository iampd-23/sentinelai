package com.sentinelai.telemetry.service;

import com.sentinelai.telemetry.dto.logTelemetryRequest;
import com.sentinelai.telemetry.event.logTelemetryEvent;
import com.sentinelai.telemetry.producer.logTelemetryProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class logTelemetryService {

    private final logTelemetryProducer logTelemetryProducer;

    public logTelemetryService(
            logTelemetryProducer logTelemetryProducer
    ) {
        this.logTelemetryProducer = logTelemetryProducer;
    }

    public CompletableFuture<?> processLogTelemetry(
            logTelemetryRequest request
    ) {

        Instant receivedAt = Instant.now();

        logTelemetryEvent event = new logTelemetryEvent(
                UUID.randomUUID(),
                request.serviceName(),
                request.environment(),
                request.level(),
                request.message(),
                request.timestamp(),
                receivedAt
        );

        return logTelemetryProducer.publish(event);
    }
}