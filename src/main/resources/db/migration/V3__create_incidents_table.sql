CREATE TABLE incidents (
    id UUID PRIMARY KEY,

    service_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,

    severity VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL,

    detected_at TIMESTAMPTZ NOT NULL,

    acknowledged_at TIMESTAMPTZ,

    resolved_at TIMESTAMPTZ,

    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_incident_service
        FOREIGN KEY (service_id)
        REFERENCES services(id),

    CONSTRAINT chk_incident_severity
        CHECK (severity IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),

    CONSTRAINT chk_incident_status
        CHECK (status IN ('OPEN', 'ACKNOWLEDGED', 'RESOLVED'))
);

CREATE INDEX idx_incidents_service_detected
    ON incidents (service_id, detected_at DESC);

CREATE INDEX idx_incidents_status
    ON incidents (status);

CREATE INDEX idx_incidents_severity
    ON incidents (severity);