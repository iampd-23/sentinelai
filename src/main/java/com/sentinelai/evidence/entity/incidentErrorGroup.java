package com.sentinelai.evidence.entity;

import com.sentinelai.incident.entity.incident;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "incident_error_groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_incident_error_group",
                        columnNames = {"incident_id", "message"})
        }
)
public class incidentErrorGroup {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private incident incident;

    @Column(nullable = false, length = 5000)
    private String message;

    @Column(nullable = false)
    private long occurrenceCount;

    protected incidentErrorGroup() {
    }

    public incidentErrorGroup(
            incident incident,
            String message,
            long occurrenceCount) {

        this.incident = incident;
        this.message = message;
        this.occurrenceCount = occurrenceCount;
    }

    public UUID getId() {
        return id;
    }

    public incident getIncident() {
        return incident;
    }

    public String getMessage() {
        return message;
    }

    public long getOccurrenceCount() {
        return occurrenceCount;
    }
}