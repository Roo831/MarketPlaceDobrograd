-- Вставка тестовых данных в таблицу users
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES
    ('user1', 'user1@example.com', '{noop}password1', 'John', 'Doe', 'STEAM_0:1:12345678', 'user', FALSE, FALSE),
    ('admin1', 'admin@example.com', '{noop}password2', 'Jane', 'Doe', 'STEAM_0:1:87654321', 'admin', TRUE, FALSE),
    ('banned_user', 'banned@example.com', '{noop}password3', 'Banned', 'User ', 'STEAM_0:1:11223344', 'banned', FALSE, TRUE);

-- Вставка тестовых данных в таблицу shops
INSERT INTO shops (user_id, rating, specialization, address, name, active, description)
VALUES
    (1, 5, 'chief', 'Линкольн 2', 'Best Chef Shop', TRUE, 'Список товаров: ...'),
    (2, 4, 'gundealer', 'Франклин 5', 'Gun Store', TRUE, 'Список товаров: ...');

-- Вставка тестовых данных в таблицу banned
INSERT INTO banned (user_id, description_of_ban)
VALUES
    (3, 'Много выебывался');

-- Вставка тестовых данных в таблицу orders
INSERT INTO orders (user_id, shop_id, status, name, description, price)
VALUES
    (1, 1, 'pending', 'Order 1', '2 Пиццы Вулкано.', 2200),
    (2, 2, 'completed', 'Order 2', 'Глок и 2 пачки патронов', 14000);