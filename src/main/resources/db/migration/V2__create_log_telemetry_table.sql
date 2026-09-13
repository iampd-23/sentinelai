CREATE TABLE log_telemetry (
    event_id UUID PRIMARY KEY,

    service_name VARCHAR(100) NOT NULL,

    environment VARCHAR(50) NOT NULL,

    level VARCHAR(20) NOT NULL,

    message VARCHAR(5000) NOT NULL,

    event_timestamp TIMESTAMPTZ NOT NULL,

    received_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_log_telemetry_service_time
    ON log_telemetry (service_name, event_timestamp);

CREATE INDEX idx_log_telemetry_environment_time
    ON log_telemetry (environment, event_timestamp);

CREATE INDEX idx_log_telemetry_level
    ON log_telemetry (level);