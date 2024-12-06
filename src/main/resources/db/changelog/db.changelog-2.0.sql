--liquibase formatted sql

-- changeset rpoptsov:1
-- Создание пользователей

-- Администратор
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('Roo831', 'helpmeplsmen@gmail.com', '$2a$12$JJmpDJaD92iK6Qd2PaoTzu.zAMr0zth8q2jwjg4qtToW7WQ6V.lKe', 'Admin', 'Adminovich', 'STEAM_1:0:203160294', 'admin', TRUE, FALSE);
