--liquibase formatted sql

--changeset rpoptsov:1
CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    steam_id    VARCHAR(64) NOT NULL UNIQUE,                                                                -- Steam id
    username    VARCHAR(50)  NOT NULL,                                                                      -- Steam username
    firstname   VARCHAR(64),                                                                                -- РП имя
    lastname    VARCHAR(64),                                                                                -- РП фамилия
    role        VARCHAR(20)  NOT NULL DEFAULT 'user' CHECK (role IN ('admin', 'user', 'banned')),           -- роль.
    is_admin    Boolean      NOT NULL                                             DEFAULT FALSE,            -- администратор?
    is_banned   Boolean      NOT NULL                                             DEFAULT FALSE,            -- забанен?
    created_at  TIMESTAMP                                                         DEFAULT CURRENT_TIMESTAMP -- дата создания
);

CREATE TABLE shops
(

    id             SERIAL PRIMARY KEY,
    user_id        INTEGER      NOT NULL UNIQUE,                                            -- владелец магазина
    rating         INTEGER CHECK (rating >= 1 AND rating <= 5),                             --оценка
    specialization VARCHAR      NOT NULL CHECK ( specialization IN ('chief', 'gundealer')), -- специализация
    address        VARCHAR(64)  NOT NULL UNIQUE,                                            --адрес магазина
    name           VARCHAR(128) Not Null UNIQUE,                                            --имя магазина
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                                     --дата создания
    active         Boolean      NOT NULL,                                                   --магазин отображается на главной странице
    description    TEXT,                                                                    -- описание магазина (перечень товаров например)
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

CREATE TABLE banned
(
    id                 SERIAL PRIMARY KEY,
    user_id            INTEGER NOT NULL UNIQUE,             --ид забаненного пользователя
    description_of_ban VARCHAR(256),                        -- описание бана
    ban_date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата бана
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER      NOT NULL,                                                                   -- ИД заказчика
    shop_id     INTEGER      NOT NULL,                                                                   -- ИД магазина в котором будет совершена покупка заказчиком
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                                                     -- время создания заказа
    status      VARCHAR(20)  NOT NULL CHECK (status IN ('pending', 'during', 'completed', 'cancelled')), -- "На рассмотрении", "В обработке", "Готов", "Отменен"
    name        VARCHAR(256) NOT NULL,                                                                   -- название заказа
    description    TEXT,                                                                                    -- описание заказа. Здесь указывается все то, что хочет приобрести клиент.
    price       INTEGER      NOT NULL,                                                                   -- цена, которую предлагает клиент. Если цена не устраивает продавца, он может пометить
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE
);
