CREATE TABLE IF NOT EXISTS users (
    id SERIAL       PRIMARY KEY,
    legal_name      VARCHAR(100)    NOT NULL,
    document_type   VARCHAR(32)     NOT NULL,
    document_number VARCHAR(32)     NOT NULL UNIQUE,
    email           VARCHAR(100)    NOT NULL UNIQUE,
    phone_number    VARCHAR(32)     UNIQUE,
    password        VARCHAR(50)     NOT NULL,
    balance         NUMERIC(19,2)   NOT NULL DEFAULT 0.00,
    user_type       VARCHAR(32)     NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);
