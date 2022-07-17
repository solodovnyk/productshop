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