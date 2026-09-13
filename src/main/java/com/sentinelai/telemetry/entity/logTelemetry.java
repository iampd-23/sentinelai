package com.sentinelai.telemetry.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "log_telemetry")
public class logTelemetry {

    @Id
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "environment", nullable = false, length = 50)
    private String environment;

    @Column(name = "level", nullable = false, length = 20)
    private String level;

    @Column(name = "message", nullable = false, length = 5000)
    private String message;

    @Column(name = "event_timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    protected logTelemetry() {
        // Required by JPA
    }

    public logTelemetry(
            UUID eventId,
            String serviceName,
            String environment,
            String level,
            String message,
            Instant timestamp,
            Instant receivedAt
    ) {
        this.eventId = eventId;
        this.serviceName = serviceName;
        this.environment = environment;
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
        this.receivedAt = receivedAt;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}