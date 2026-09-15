CREATE TABLE incident_error_groups (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    incident_id UUID NOT NULL,
    message VARCHAR(5000) NOT NULL,
    occurrence_count BIGINT NOT NULL,

    CONSTRAINT fk_incident_error_group_incident
        FOREIGN KEY (incident_id)
        REFERENCES incidents(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_incident_error_group
        UNIQUE (incident_id, message),

    CONSTRAINT chk_incident_error_group_count
        CHECK (occurrence_count > 0)
);

CREATE INDEX idx_incident_error_groups_incident
    ON incident_error_groups (incident_id);