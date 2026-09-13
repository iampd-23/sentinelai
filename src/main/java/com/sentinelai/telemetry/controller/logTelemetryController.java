package com.sentinelai.telemetry.controller;

import com.sentinelai.telemetry.dto.logTelemetryRequest;
import com.sentinelai.telemetry.service.logTelemetryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/telemetry/logs")
public class logTelemetryController {

    private final logTelemetryService logTelemetryService;

    public logTelemetryController(
            logTelemetryService logTelemetryService
    ) {
        this.logTelemetryService = logTelemetryService;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Void>> ingestLog(
            @Valid @RequestBody logTelemetryRequest request
    ) {
        return logTelemetryService
                .processLogTelemetry(request)
                .thenApply(result ->
                        ResponseEntity.accepted().build()
                );
    }
}