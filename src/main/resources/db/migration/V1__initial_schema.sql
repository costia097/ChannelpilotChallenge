CREATE TABLE story
(
    id          SERIAL PRIMARY KEY,
    story_id     BIGINT NOT NULL,
    descendants INTEGER,
    score       INTEGER,
    title       VARCHAR,
    url         VARCHAR
);

CREATE TABLE comment
(
    id         SERIAL PRIMARY KEY,
    comment_id BIGINT    NOT NULL,
    time       TIMESTAMP NOT NULL,
    text       VARCHAR,
    by         VARCHAR,
    parent_id  BIGINT,
    story_id   INTEGER REFERENCES story (id)
);
