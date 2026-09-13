package com.sentinelai.service.repository;

import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.entity.serviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface monitoredServiceRepository extends JpaRepository<monitoredService, UUID> {

    List<monitoredService> findByEnvironment(String environment);

    List<monitoredService> findByStatus(serviceStatus status);

    boolean existsByNameAndEnvironment(String name, String environment);
}