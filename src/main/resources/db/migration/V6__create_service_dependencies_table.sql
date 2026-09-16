CREATE TABLE service_dependencies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    source_service_id UUID NOT NULL,
    target_service_id UUID NOT NULL,

    dependency_type VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_service_dependency_source
        FOREIGN KEY (source_service_id)
        REFERENCES services(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_service_dependency_target
        FOREIGN KEY (target_service_id)
        REFERENCES services(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_service_dependency
        UNIQUE (source_service_id, target_service_id),

    CONSTRAINT chk_service_dependency_self_reference
        CHECK (source_service_id <> target_service_id)
);

CREATE INDEX idx_service_dependencies_source
    ON service_dependencies (source_service_id);

CREATE INDEX idx_service_dependencies_target
    ON service_dependencies (target_service_id);