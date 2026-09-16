package com.sentinelai.rca.entity;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.service.entity.monitoredService;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "root_cause_candidates",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_root_cause_candidate",
                columnNames = {"incident_id", "candidate_service_id"}
        )
)
public class rootCauseCandidate {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private incident incident;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_service_id", nullable = false)
    private monitoredService candidateService;

    @Column(nullable = false, length = 5000)
    private String reason;

    @Column(nullable = false)
    private double confidence;

    protected rootCauseCandidate() {
    }

    public rootCauseCandidate(
            incident incident,
            monitoredService candidateService,
            String reason,
            double confidence) {

        this.incident = incident;
        this.candidateService = candidateService;
        this.reason = reason;
        this.confidence = confidence;
    }

    public UUID getId() {
        return id;
    }

    public incident getIncident() {
        return incident;
    }

    public monitoredService getCandidateService() {
        return candidateService;
    }

    public String getReason() {
        return reason;
    }

    public double getConfidence() {
        return confidence;
    }
}