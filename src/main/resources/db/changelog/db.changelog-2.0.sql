--liquibase formatted sql

-- changeset rpoptsov:1
-- Создание пользователей

-- Администратор
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('admin_user', 'admin@example.com', '\$2a\$10$ELiXds2FWU5li3Rq0PCPJuFC2fs/Hzy6Mzn9Xi1YdW/d2l9vSjZNG', 'Admin', 'Adminovich', 'STEAM_0:1:12345678', 'admin', TRUE, FALSE);

-- Забаненный пользователь
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('banned_user', 'banned@example.com', '\$2a\$10$KYatWC1s8o/kcdNUgipMfOQ78GcDcc.rv6PaySIBt3SYGJ0/A21MO', 'Banned', 'Bannedov', 'STEAM_0:1:87654321', 'banned', FALSE, TRUE);

-- Продавец
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('seller_user', 'seller@example.com', '\$2a\$10$Jc3D3N9O1z4Q1EoK3xZ5hOeJ1z4Q1EoK3xZ5hOeJ1z4Q1EoK3xZ5hO', 'Seller', 'Sellerman', 'STEAM_0:1:11223344', 'user', FALSE, FALSE);

-- Покупатель
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES ('buyer_user', 'buyer@example.com', '\$2a\$10$Jc3D3N9O1z4Q1EoK3xZ5hOeJ1z4Q1EoK3xZ5hOeJ1z4Q1EoK3xZ5hO', 'Buyer', 'Buyerman', 'STEAM_0:1:22334455', 'user', FALSE, FALSE);

-- Добавление забаненного пользователя в таблицу banned
INSERT INTO banned (user_id, description_of_ban)
VALUES (2, 'Нарушение правил сообщества');

-- Создание магазина для продавца
INSERT INTO shops (user_id, rating, specialization, address, name, active, description)
VALUES (3, 5, 'chief', '123 Seller St', 'Best Seller Shop', TRUE, 'Магазин лучшего продавца.');

-- Создание заказов для покупателя
INSERT INTO orders (user_id, shop_id, status, name, description, price)
VALUES
    (4, 1, 'pending', 'Order 1', 'Покупка товара A', 100),
    (4, 1, 'pending', 'Order 2', 'Покупка товара B', 200),
    (4, 1, 'completed', 'Order 3', 'Покупка товара C', 150);