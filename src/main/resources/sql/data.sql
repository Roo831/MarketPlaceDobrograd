-- Вставка тестовых данных в таблицу users
INSERT INTO users (username, email, password, firstname, lastname, steam_id, role, is_admin, is_banned)
VALUES
    ('user1', 'user1@example.com', 'password1', 'John', 'Doe', 'STEAM_0:1:12345678', 'user', FALSE, FALSE),
    ('admin1', 'admin@example.com', 'password2', 'Jane', 'Doe', 'STEAM_0:1:87654321', 'admin', TRUE, FALSE),
    ('banned_user', 'banned@example.com', 'password3', 'Banned', 'User ', 'STEAM_0:1:11223344', 'banned', FALSE, TRUE);

-- Вставка тестовых данных в таблицу shops
INSERT INTO shops (user_id, rating, specialization, address, name, is_active, description)
VALUES
    (1, 5, 'chief', '123 Main St', 'Best Chef Shop', TRUE, 'We offer the best culinary products.'),
    (2, 4, 'gundealer', '456 Side St', 'Gun Store', TRUE, 'Quality firearms and accessories.');

-- Вставка тестовых данных в таблицу banned
INSERT INTO banned (user_id, description_of_ban)
VALUES
    (3, 'Banned for violating rules.');

-- Вставка тестовых данных в таблицу orders
INSERT INTO orders (user_id, shop_id, status, name, description, price)
VALUES
    (1, 1, 'pending', 'Order 1', 'I would like to buy some culinary items.', 100),
    (2, 2, 'completed', 'Order 2', 'Purchasing a new firearm.', 500);