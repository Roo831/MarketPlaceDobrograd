
CREATE TABLE users
(
    id               SERIAL PRIMARY KEY,
    username         VARCHAR(50)  NOT NULL UNIQUE,
    email            VARCHAR(100) NOT NULL UNIQUE,
    password         VARCHAR(255) NOT NULL,
    firstname        VARCHAR(64),
    lastname         VARCHAR(64),
    steam_id         VARCHAR(64),
    role             VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'SELLER', 'BUYER', 'BANNED'))
);

CREATE TABLE seller
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL UNIQUE,
    rating      INTEGER CHECK (rating >= 1 AND rating <= 5),
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE product
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    price       INTEGER      NOT NULL,
    category    VARCHAR(20)  NOT NULL CHECK (category IN ('GUNS', 'FOOD')),
    seller_id   INTEGER      NOT NULL,
    is_active   BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (seller_id) REFERENCES seller (id) ON DELETE CASCADE
);

CREATE TABLE admin
(
    id      SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE banned
(
    id                 SERIAL PRIMARY KEY,
    user_id            INTEGER NOT NULL UNIQUE,
    description_of_ban VARCHAR(256),
    ban_date           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE purchase_history
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL,
    purchase_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE purchase_item
(
    id              SERIAL PRIMARY KEY,
    purchase_id     INTEGER NOT NULL,  -- Ссылка на покупку
    product_id      INTEGER NOT NULL,   -- Идентификатор продукта
    quantity        INTEGER NOT NULL,    -- Количество продукта
    FOREIGN KEY (purchase_id) REFERENCES purchase_history (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);




