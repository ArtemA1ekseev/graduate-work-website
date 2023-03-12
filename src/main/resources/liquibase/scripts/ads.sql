-- liquibase formatted sql

-- changeset aalekseev:1

CREATE TABLE IF NOT EXISTS ads
(
    id          BIGSERIAL PRIMARY KEY,
    author_id   integer REFERENCES users (id),
    image       text,
    price       int  NOT NULL,
    title       varchar,
    description text NOT NULL
);