package com.sentinelai.evidence.entity;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.telemetry.entity.logTelemetry;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "incident_evidence")
public class incidentEvidence {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private incident incident;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "telemetry_id",
            referencedColumnName = "event_id",
            nullable = false)
    private logTelemetry telemetry;

    @Column(nullable = false)
    private Instant correlatedAt;

    protected incidentEvidence() {
    }

    public incidentEvidence(
            incident incident,
            logTelemetry telemetry,
            Instant correlatedAt) {

        this.incident = incident;
        this.telemetry = telemetry;
        this.correlatedAt = correlatedAt;
    }

    public UUID getId() {
        return id;
    }

    public incident getIncident() {
        return incident;
    }

    public logTelemetry getTelemetry() {
        return telemetry;
    }

    public Instant getCorrelatedAt() {
        return correlatedAt;
    }
}