ALTER TABLE subscription
    ADD CONSTRAINT unique_user_service UNIQUE (user_id, service_name);
