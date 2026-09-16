CREATE TABLE root_cause_candidates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    incident_id UUID NOT NULL,
    candidate_service_id UUID NOT NULL,

    reason VARCHAR(5000) NOT NULL,
    confidence DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_root_cause_candidate_incident
        FOREIGN KEY (incident_id)
        REFERENCES incidents(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_root_cause_candidate_service
        FOREIGN KEY (candidate_service_id)
        REFERENCES services(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_root_cause_candidate
        UNIQUE (incident_id, candidate_service_id),

    CONSTRAINT chk_root_cause_confidence
        CHECK (confidence >= 0.0 AND confidence <= 1.0)
);

CREATE INDEX idx_root_cause_candidates_incident
    ON root_cause_candidates (incident_id);

CREATE INDEX idx_root_cause_candidates_service
    ON root_cause_candidates (candidate_service_id);