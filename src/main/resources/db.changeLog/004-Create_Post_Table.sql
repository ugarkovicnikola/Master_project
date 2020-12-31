CREATE TABLE post (
    id BIGINT   PRIMARY KEY NOT NULL AUTO_INCREMENT,
    content     VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    student_id  BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES user(id)
);