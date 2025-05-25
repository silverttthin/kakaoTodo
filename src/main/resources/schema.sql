CREATE TABLE IF NOT EXISTS todos
(
    id         INT      NOT NULL AUTO_INCREMENT,
    content    TEXT     NOT NULL,
    creator    TEXT     NOT NULL,
    password   TEXT     NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;