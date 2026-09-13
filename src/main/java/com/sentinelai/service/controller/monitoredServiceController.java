package com.sentinelai.service.controller;

import com.sentinelai.service.dto.monitoredServiceCreateRequest;
import com.sentinelai.service.dto.monitoredServiceResponse;
import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.service.monitoredServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
public class monitoredServiceController {

    private final monitoredServiceService monitoredServiceService;

    public monitoredServiceController(
            monitoredServiceService monitoredServiceService
    ) {
        this.monitoredServiceService = monitoredServiceService;
    }

    @PostMapping
    public ResponseEntity<monitoredServiceResponse> createService(
            @Valid @RequestBody monitoredServiceCreateRequest request
    ) {
        monitoredService service =
                monitoredServiceService.createService(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(monitoredServiceResponse.fromEntity(service));
    }

    @GetMapping
    public ResponseEntity<List<monitoredServiceResponse>> getAllServices() {
        List<monitoredServiceResponse> response =
                monitoredServiceService.getAllServices()
                        .stream()
                        .map(monitoredServiceResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<monitoredServiceResponse> getServiceById(
            @PathVariable UUID id
    ) {
        monitoredService service =
                monitoredServiceService.getServiceById(id);

        return ResponseEntity.ok(
                monitoredServiceResponse.fromEntity(service)
        );
    }

    @GetMapping("/environment/{environment}")
    public ResponseEntity<List<monitoredServiceResponse>> getByEnvironment(
            @PathVariable String environment
    ) {
        List<monitoredServiceResponse> response =
                monitoredServiceService
                        .getServicesByEnvironment(environment)
                        .stream()
                        .map(monitoredServiceResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(response);
    }
}