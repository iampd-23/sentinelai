package com.sentinelai.incident.entity;

import com.sentinelai.service.entity.monitoredService;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "incidents")
public class incident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private monitoredService monitoredService;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private incidentSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private incidentStatus status;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    @Column(name = "acknowledged_at")
    private Instant acknowledgedAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected incident() {
        // Required by JPA
    }

    public incident(
            monitoredService monitoredService,
            String title,
            incidentSeverity severity,
            incidentStatus status,
            Instant detectedAt
    ) {
        this.monitoredService = monitoredService;
        this.title = title;
        this.severity = severity;
        this.status = status;
        this.detectedAt = detectedAt;
        this.updatedAt = detectedAt;
    }

    public UUID getId() {
        return id;
    }

    public monitoredService getMonitoredService() {
        return monitoredService;
    }

    public String getTitle() {
        return title;
    }

    public incidentSeverity getSeverity() {
        return severity;
    }

    public incidentStatus getStatus() {
        return status;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public Instant getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void acknowledge(Instant acknowledgedAt) {
        this.status = incidentStatus.ACKNOWLEDGED;
        this.acknowledgedAt = acknowledgedAt;
        this.updatedAt = acknowledgedAt;
    }

    public void resolve(Instant resolvedAt) {
        this.status = incidentStatus.RESOLVED;
        this.resolvedAt = resolvedAt;
        this.updatedAt = resolvedAt;
    }
}