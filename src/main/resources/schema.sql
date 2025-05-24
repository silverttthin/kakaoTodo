DROP TABLE IF EXISTS todos;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INT          NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE todos
(
    id         INT      NOT NULL AUTO_INCREMENT,
    author_id  INT      NOT NULL,
    content    TEXT     NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    INDEX      idx_todos_author (author_id),
    CONSTRAINT fk_todos_author
        FOREIGN KEY (author_id)
            REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
