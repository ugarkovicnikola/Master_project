CREATE TABLE comment (
    id BIGINT      PRIMARY KEY NOT NULL AUTO_INCREMENT,
    text            VARCHAR(255) NOT NULL,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    student_id      BIGINT,
    professor_id    BIGINT,
    office_id       BIGINT,
    post_id         BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (professor_id) REFERENCES professor(id),
    FOREIGN KEY (office_id) REFERENCES office(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);