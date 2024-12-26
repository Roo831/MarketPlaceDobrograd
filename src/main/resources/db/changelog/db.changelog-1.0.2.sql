--liquibase formatted sql

--changeset rpoptsov:2

ALTER TABLE shops
ALTER COLUMN description TYPE VARCHAR(2000) USING description::VARCHAR(2000);
ALTER TABLE orders
ALTER COLUMN description TYPE VARCHAR(2000) USING description::VARCHAR(2000);