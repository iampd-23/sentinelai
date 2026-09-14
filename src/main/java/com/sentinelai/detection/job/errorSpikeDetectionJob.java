package com.sentinelai.detection.job;

import com.sentinelai.detection.service.incidentDetectionService;
import com.sentinelai.service.entity.monitoredService;
import com.sentinelai.service.repository.monitoredServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class errorSpikeDetectionJob {

    private static final Logger log =
            LoggerFactory.getLogger(errorSpikeDetectionJob.class);

    private final monitoredServiceRepository monitoredServiceRepository;
    private final incidentDetectionService incidentDetectionService;

    public errorSpikeDetectionJob(
            monitoredServiceRepository monitoredServiceRepository,
            incidentDetectionService incidentDetectionService) {

        this.monitoredServiceRepository = monitoredServiceRepository;
        this.incidentDetectionService = incidentDetectionService;
    }

    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    public void detectErrorSpikes() {

        log.info("ERROR SPIKE DETECTION JOB STARTED");

        for (monitoredService service : monitoredServiceRepository.findAll()) {

            log.info(
                    "Checking service: {} / {}",
                    service.getName(),
                    service.getEnvironment());

            incidentDetectionService.detectForService(
                    service.getId(),
                    service.getName(),
                    service.getEnvironment());
        }

        log.info("ERROR SPIKE DETECTION JOB FINISHED");
    }
}