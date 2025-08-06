CREATE TABLE IF NOT EXISTS transactions (
    id              BIGSERIAL PRIMARY KEY,
    sender_id       INTEGER         NOT NULL,
    recipient_id    INTEGER         NOT NULL,
    amount          NUMERIC(19,2)   NOT NULL CHECK (amount > 0),
    status          VARCHAR(10)     NOT NULL DEFAULT 'PENDING',
    description     TEXT,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_recipient FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);
