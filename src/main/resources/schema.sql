CREATE TABLE IF NOT EXISTS users (
                                     id          INT          NOT NULL AUTO_INCREMENT,
                                     name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME     NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2) todos 테이블
CREATE TABLE IF NOT EXISTS todos (
                                     id          INT      NOT NULL AUTO_INCREMENT,
                                     user_id     INT      NOT NULL,
                                     content     TEXT     NOT NULL,
                                     password    TEXT     NOT NULL,
                                     created_at  DATETIME NOT NULL,
                                     updated_at  DATETIME NOT NULL,
                                     PRIMARY KEY (id),
    INDEX idx_todos_user_id (user_id),
    CONSTRAINT fk_todos_users
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;