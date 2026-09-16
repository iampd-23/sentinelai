package com.sentinelai.dependency.repository;

import com.sentinelai.dependency.entity.serviceDependency;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface serviceDependencyRepository
        extends JpaRepository<serviceDependency, UUID> {

    @EntityGraph(attributePaths = {"sourceService", "targetService"})
    List<serviceDependency> findAll();

    @EntityGraph(attributePaths = {"sourceService", "targetService"})
    List<serviceDependency> findBySourceServiceId(UUID sourceServiceId);

    @EntityGraph(attributePaths = {"sourceService", "targetService"})
    List<serviceDependency> findByTargetServiceId(UUID targetServiceId);

    boolean existsBySourceServiceIdAndTargetServiceId(
            UUID sourceServiceId,
            UUID targetServiceId);
}