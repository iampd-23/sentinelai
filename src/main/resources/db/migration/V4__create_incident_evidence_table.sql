CREATE TABLE incident_evidence (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    incident_id UUID NOT NULL,
    telemetry_id UUID NOT NULL,

    correlated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_incident_evidence_incident
        FOREIGN KEY (incident_id)
        REFERENCES incidents(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_incident_evidence_telemetry
        FOREIGN KEY (telemetry_id)
        REFERENCES log_telemetry(event_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_incident_evidence
        UNIQUE (incident_id, telemetry_id)
);