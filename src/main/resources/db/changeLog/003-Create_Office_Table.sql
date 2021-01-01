CREATE TABLE office(
    id BIGINT   PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6)
);