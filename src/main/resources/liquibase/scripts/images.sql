-- liquibase formatted sql

-- changeset aalekseev:1

CREATE TABLE IF NOT EXISTS images
(
    id         BIGSERIAL PRIMARY KEY,
    file_path  varchar NOT NULL,
    image      bytea   NOT NULL,
    file_size  integer NOT NULL,
    ads_id     integer REFERENCES ads (id),
    media_type text
);