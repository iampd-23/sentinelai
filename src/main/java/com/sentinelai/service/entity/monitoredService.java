package com.sentinelai.service.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "services",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_service_name_environment",
                        columnNames = {"name", "environment"}
                )
        }
)
public class monitoredService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String environment;

    @Column(name = "owner_team", nullable = false, length = 100)
    private String ownerTeam;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private serviceStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected monitoredService() {
        // Required by JPA
    }

    public monitoredService(
            String name,
            String environment,
            String ownerTeam,
            serviceStatus status
    ) {
        this.name = name;
        this.environment = environment;
        this.ownerTeam = ownerTeam;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = serviceStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public serviceStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void updateStatus(serviceStatus status) {
        this.status = status;
    }
}