package com.sentinelai.dependency.entity;

import com.sentinelai.service.entity.monitoredService;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "service_dependencies",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_service_dependency",
                columnNames = {"source_service_id", "target_service_id"}
        )
)
public class serviceDependency {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_service_id", nullable = false)
    private monitoredService sourceService;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_service_id", nullable = false)
    private monitoredService targetService;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type", nullable = false, length = 50)
    private dependencyType dependencyType;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected serviceDependency() {
    }

    public serviceDependency(
            monitoredService sourceService,
            monitoredService targetService,
            dependencyType dependencyType) {

        this.sourceService = sourceService;
        this.targetService = targetService;
        this.dependencyType = dependencyType;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public monitoredService getSourceService() {
        return sourceService;
    }

    public monitoredService getTargetService() {
        return targetService;
    }

    public dependencyType getDependencyType() {
        return dependencyType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}