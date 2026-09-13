CREATE TABLE services (
    id UUID PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    environment VARCHAR(50) NOT NULL,

    owner_team VARCHAR(100) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT uk_service_name_environment
        UNIQUE (name, environment),

    CONSTRAINT chk_service_status
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'MAINTENANCE'))
);

CREATE INDEX idx_services_environment
    ON services (environment);

CREATE INDEX idx_services_status
    ON services (status);