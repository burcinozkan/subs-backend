-- V4__fix_created_at_nullable.sql

-- Kolon yoksa ekle
ALTER TABLE subscription
    ADD COLUMN created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP;
