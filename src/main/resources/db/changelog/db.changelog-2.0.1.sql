--liquibase formatted sql

-- changeset rpoptsov:1
-- Создание пользователей

-- Администратор
INSERT INTO users (username, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('Roo', 'Doc', 'Norton', '76561198366586316', 'admin', TRUE, FALSE);
