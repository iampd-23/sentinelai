package com.sentinelai.telemetry.producer;

import com.sentinelai.telemetry.event.logTelemetryEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class logTelemetryProducer {

    private static final String TELEMETRY_LOGS_TOPIC = "telemetry.logs";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public logTelemetryProducer(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<?> publish(
            logTelemetryEvent event
    ) {
        return kafkaTemplate.send(
                TELEMETRY_LOGS_TOPIC,
                event.eventId().toString(),
                event
        );
    }
}