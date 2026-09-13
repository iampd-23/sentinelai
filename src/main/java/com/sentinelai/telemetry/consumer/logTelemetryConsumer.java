package com.sentinelai.telemetry.consumer;

import com.sentinelai.telemetry.event.logTelemetryEvent;
import com.sentinelai.telemetry.service.logTelemetryEventProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class logTelemetryConsumer {

    private static final String TELEMETRY_LOGS_TOPIC = "telemetry.logs";

    private final logTelemetryEventProcessor logTelemetryEventProcessor;

    public logTelemetryConsumer(
            logTelemetryEventProcessor logTelemetryEventProcessor
    ) {
        this.logTelemetryEventProcessor = logTelemetryEventProcessor;
    }

    @KafkaListener(
            topics = TELEMETRY_LOGS_TOPIC,
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(logTelemetryEvent event) {

        logTelemetryEventProcessor.process(event);
    }
}