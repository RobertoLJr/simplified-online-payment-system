CREATE TABLE IF NOT EXISTS notifications (
    id              BIGSERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL,
    transaction_id  INTEGER,
    channel         VARCHAR(20) NOT NULL CHECK (channel IN ('EMAIL', 'PHONE')),
    destination     VARCHAR(100) NOT NULL,
    subject         VARCHAR(150),
    message         TEXT NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    sent_at         TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_transaction_notification FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE SET NULL
    );
