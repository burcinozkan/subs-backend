CREATE TABLE usage_report (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              service_name VARCHAR(255) NOT NULL,
                              total_minutes INT NOT NULL,
                              recorded_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_user_usage FOREIGN KEY (user_id) REFERENCES users(id)
);
