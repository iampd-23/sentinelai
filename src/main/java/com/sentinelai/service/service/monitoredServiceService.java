package com.sentinelai.service.service;

import com.sentinelai.common.exception.serviceAlreadyExistsException;
import com.sentinelai.common.exception.serviceNotFoundException;
import com.sentinelai.service.dto.monitoredServiceCreateRequest;
import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.entity.serviceStatus;
import com.sentinelai.service.repository.monitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class monitoredServiceService {

    private final monitoredServiceRepository monitoredServiceRepository;

    public monitoredServiceService(
            monitoredServiceRepository monitoredServiceRepository
    ) {
        this.monitoredServiceRepository = monitoredServiceRepository;
    }

    public monitoredService createService(
            monitoredServiceCreateRequest request
    ) {
        if (monitoredServiceRepository.existsByNameAndEnvironment(
                request.name(),
                request.environment()
        )) {
            throw new serviceAlreadyExistsException(
                    "Service already exists for environment: "
                            + request.name()
                            + " / "
                            + request.environment()
            );
        }

        monitoredService monitoredService = new monitoredService(
                request.name(),
                request.environment(),
                request.ownerTeam(),
                request.status()
        );

        return monitoredServiceRepository.save(monitoredService);
    }

    @Transactional(readOnly = true)
    public monitoredService getServiceById(UUID id) {
        return monitoredServiceRepository.findById(id)
                .orElseThrow(() ->
                        new serviceNotFoundException(
                                "Service not found: " + id
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<monitoredService> getAllServices() {
        return monitoredServiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<monitoredService> getServicesByEnvironment(
            String environment
    ) {
        return monitoredServiceRepository.findByEnvironment(environment);
    }

    @Transactional(readOnly = true)
    public List<monitoredService> getServicesByStatus(
            serviceStatus status
    ) {
        return monitoredServiceRepository.findByStatus(status);
    }
}