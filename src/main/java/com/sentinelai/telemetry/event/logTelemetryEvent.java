package com.sentinelai.telemetry.event;

import java.time.Instant;
import java.util.UUID;

public record logTelemetryEvent(

        UUID eventId,

        String serviceName,

        String environment,

        String level,

        String message,

        Instant timestamp,

        Instant receivedAt

) {
}