package com.sentinelai.dependency.controller;

import com.sentinelai.dependency.dto.serviceDependencyCreateRequest;
import com.sentinelai.dependency.dto.serviceDependencyResponse;
import com.sentinelai.dependency.entity.serviceDependency;
import com.sentinelai.dependency.service.serviceDependencyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dependencies")
public class serviceDependencyController {

    private final serviceDependencyService serviceDependencyService;

    public serviceDependencyController(
            serviceDependencyService serviceDependencyService) {
        this.serviceDependencyService = serviceDependencyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public serviceDependencyResponse createDependency(
            @Valid @RequestBody serviceDependencyCreateRequest request) {

        serviceDependency dependency =
                serviceDependencyService.createDependency(
                        request.sourceServiceId(),
                        request.targetServiceId(),
                        request.dependencyType());

        return toResponse(dependency);
    }

    @GetMapping
    public List<serviceDependencyResponse> getAllDependencies() {
        return serviceDependencyService.getAllDependencies()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/source/{sourceServiceId}")
    public List<serviceDependencyResponse> getDependenciesForSource(
            @PathVariable UUID sourceServiceId) {

        return serviceDependencyService
                .getDependenciesForSourceService(sourceServiceId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/target/{targetServiceId}")
    public List<serviceDependencyResponse> getDependenciesForTarget(
            @PathVariable UUID targetServiceId) {

        return serviceDependencyService
                .getDependenciesForTargetService(targetServiceId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private serviceDependencyResponse toResponse(
            serviceDependency dependency) {

        return new serviceDependencyResponse(
                dependency.getId(),
                dependency.getSourceService().getId(),
                dependency.getSourceService().getName(),
                dependency.getTargetService().getId(),
                dependency.getTargetService().getName(),
                dependency.getDependencyType(),
                dependency.getCreatedAt());
    }
}