package com.sentinelai.telemetry.repository;

import com.sentinelai.telemetry.entity.logTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.time.Instant;
import java.util.UUID;

public interface logTelemetryRepository
    extends JpaRepository<logTelemetry, UUID> {

  @Query("""
      SELECT COUNT(l)
      FROM logTelemetry l
      WHERE l.serviceName = :serviceName
        AND l.environment = :environment
        AND l.level = 'ERROR'
        AND l.receivedAt >= :from
        AND l.receivedAt < :to
      """)
  long countErrors(
      @Param("serviceName") String serviceName,
      @Param("environment") String environment,
      @Param("from") Instant from,
      @Param("to") Instant to);

  @Query("""
      SELECT l
      FROM logTelemetry l
      WHERE l.serviceName = :serviceName
        AND l.environment = :environment
        AND l.level = 'ERROR'
        AND l.receivedAt >= :from
        AND l.receivedAt < :to
      ORDER BY l.receivedAt ASC
      """)
  List<logTelemetry> findErrorLogsForCorrelation(
      @Param("serviceName") String serviceName,
      @Param("environment") String environment,
      @Param("from") Instant from,
      @Param("to") Instant to);
}