package com.sentinelai.incident.repository;

import com.sentinelai.incident.entity.incident;
import com.sentinelai.incident.entity.incidentSeverity;
import com.sentinelai.incident.entity.incidentStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface incidentRepository extends JpaRepository<incident, UUID> {

    @Override
    @EntityGraph(attributePaths = "monitoredService")
    List<incident> findAll();

    @Override
    @EntityGraph(attributePaths = "monitoredService")
    Optional<incident> findById(UUID id);

    @EntityGraph(attributePaths = "monitoredService")
    List<incident> findByStatus(incidentStatus status);

    @EntityGraph(attributePaths = "monitoredService")
    List<incident> findBySeverity(incidentSeverity severity);

    @EntityGraph(attributePaths = "monitoredService")
    List<incident> findByMonitoredServiceId(UUID serviceId);

    @EntityGraph(attributePaths = "monitoredService")
    List<incident> findByMonitoredServiceIdAndStatus(
            UUID serviceId,
            incidentStatus status
    );
}