ALTER TABLE subscription
    ADD COLUMN user_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_subscription_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;
