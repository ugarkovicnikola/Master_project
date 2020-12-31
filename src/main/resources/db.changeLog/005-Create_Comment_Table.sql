CREATE TABLE comment (
    id BIGINT   PRIMARY KEY NOT NULL AUTO_INCREMENT,
    text        VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),

);