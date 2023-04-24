-- liquibase formatted sql

-- changeset aalekseev:1

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    email      varchar NOT NULL,
    password   varchar NOT NULL,
    first_name varchar NOT NULL,
    last_name  varchar NOT NULL,
    phone      varchar NOT NULL,
    role       varchar NOT NULL
);
