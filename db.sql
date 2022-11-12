CREATE DATABASE product_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `categories` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    parent BIGINT,
    icon VARCHAR(255),
    slug VARCHAR(255),
    creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT DEFAULT 0
);

CREATE TABLE `items` (
     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     category_id BIGINT NOT NULL,
     description TEXT,
     photo VARCHAR(255),
     price BIGINT NOT NULL,
     sale TINYINT,
     creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     is_deleted TINYINT DEFAULT 0,
     FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE `messages` (
     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     sender_name VARCHAR(255) NOT NULL,
     sender_email VARCHAR(255) NOT NULL,
     message_text TEXT,
     creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     is_deleted TINYINT DEFAULT 0
);

CREATE TABLE `users` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role_id TINYINT NOT NULL,
    creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT DEFAULT 0
);

CREATE TABLE `orders` (
     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     user_id BIGINT NOT NULL,
     order_status VARCHAR(255) NOT NULL,
     creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     is_deleted TINYINT DEFAULT 0
);

CREATE TABLE `product_position` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    creating_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT DEFAULT 0,
    FOREIGN KEY(order_id) REFERENCES orders(id),
    FOREIGN KEY(item_id) REFERENCES items(id)
);