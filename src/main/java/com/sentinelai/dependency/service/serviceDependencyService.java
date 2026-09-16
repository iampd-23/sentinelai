package com.sentinelai.dependency.service;

import com.sentinelai.dependency.entity.dependencyType;
import com.sentinelai.dependency.entity.serviceDependency;
import com.sentinelai.dependency.repository.serviceDependencyRepository;
import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.repository.monitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class serviceDependencyService {

    private final serviceDependencyRepository serviceDependencyRepository;
    private final monitoredServiceRepository monitoredServiceRepository;

    public serviceDependencyService(
            serviceDependencyRepository serviceDependencyRepository,
            monitoredServiceRepository monitoredServiceRepository) {

        this.serviceDependencyRepository = serviceDependencyRepository;
        this.monitoredServiceRepository = monitoredServiceRepository;
    }

    public serviceDependency createDependency(
            UUID sourceServiceId,
            UUID targetServiceId,
            dependencyType dependencyType) {

        monitoredService sourceService = monitoredServiceRepository.findById(sourceServiceId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Source service not found: " + sourceServiceId));

        monitoredService targetService = monitoredServiceRepository.findById(targetServiceId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Target service not found: " + targetServiceId));

        boolean alreadyExists =
                serviceDependencyRepository.existsBySourceServiceIdAndTargetServiceId(
                        sourceServiceId,
                        targetServiceId);

        if (alreadyExists) {
            throw new IllegalArgumentException(
                    "Dependency already exists between source and target service");
        }

        serviceDependency dependency =
                new serviceDependency(sourceService, targetService, dependencyType);

        return serviceDependencyRepository.save(dependency);
    }

    @Transactional(readOnly = true)
    public List<serviceDependency> getAllDependencies() {
        return serviceDependencyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<serviceDependency> getDependenciesForSourceService(
            UUID sourceServiceId) {

        return serviceDependencyRepository.findBySourceServiceId(sourceServiceId);
    }

    @Transactional(readOnly = true)
    public List<serviceDependency> getDependenciesForTargetService(
            UUID targetServiceId) {

        return serviceDependencyRepository.findByTargetServiceId(targetServiceId);
    }
}