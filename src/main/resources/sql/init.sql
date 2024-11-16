CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE, -- логин. Возможно не нужен, так как аутентификация будет происходить через стим
    email      VARCHAR(100) NOT NULL UNIQUE, -- почта. Возможно не нужна, так как аутентификация будет происходить через стим
    password   VARCHAR(255) NOT NULL, -- пароль. Возможно не нужен, так как аутентификация будет происходить через стим
    firstname  VARCHAR(64), -- РП имя
    lastname   VARCHAR(64), -- РП фамилия
    steam_id   VARCHAR(64), -- steam_id
    role       VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'USER', 'BANNED')) DEFAULT 'USER', -- роль. Возможно не нужна, так как есть флаги isAdmin и isBanned
    isAdmin    Boolean      NOT NULL                                             DEFAULT FALSE, -- администратор
    isBanned   Boolean      NOT NULL                                             DEFAULT FALSE, -- забанен?
    created_at TIMESTAMP                                                         DEFAULT CURRENT_TIMESTAMP  -- дата создания
);

CREATE TABLE shops
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER     NOT NULL UNIQUE, -- владелец магазина
    rating         INTEGER CHECK (rating >= 1 AND rating <= 5), --оценка
    specialization VARCHAR     NOT NULL CHECK ( specialization IN ('CHIEF', 'GUNDEALER')), -- специализация
    address        VARCHAR(64) NOT NULL UNIQUE, --адрес магазина
    name      VARCHAR(128), --имя магазина
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP, --дата создания
    description    TEXT, -- описание магазина (перечень товаров например)
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE banned
(
    id                 SERIAL PRIMARY KEY,
    user_id            INTEGER NOT NULL UNIQUE, --ид забаненного пользователя
    description_of_ban VARCHAR(256), -- описание бана
    ban_date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата бана
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER     NOT NULL, -- ИД заказчика
    shop_id    INTEGER     NOT NULL, -- ИД магазина в котором будет совершена покупка заказчиком
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- время создания заказа
    status     VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'DURING', 'COMPLETED', 'CANCELLED')), -- "На рассмотрении", "В обработке", "Готов", "Отменен"
    name VARCHAR(256) NOT NULL, -- название заказа
    description TEXT,       -- описание заказа. Здесь указывается все то, что хочет приобрести клиент.
    price      INTEGER     NOT NULL, -- цена, которую предлагает клиент. Если цена не устраивает продавца, он может пометить
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (shop_id) REFERENCES  shops (id) ON DELETE CASCADE
);

