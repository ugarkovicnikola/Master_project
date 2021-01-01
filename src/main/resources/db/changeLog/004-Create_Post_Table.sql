CREATE TABLE post (
    id BIGINT   PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title                   VARCHAR(255) NOT NULL,
    content                 VARCHAR(255) NOT NULL,
    number_of_up_votes      BIGINT,
    number_of_down_votes    BIGINT,
    created_at              DATETIME(6),
    updated_at              DATETIME(6),
    student_id              BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(id)
);