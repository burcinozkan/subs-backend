-- db/migration/V1__init.sql

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS subscription (
                                            id BINARY(16) NOT NULL PRIMARY KEY,
    service_name VARCHAR(255),
    monthly_price DECIMAL(10,2),
    next_billing_date DATE,
    status VARCHAR(20)
    );
