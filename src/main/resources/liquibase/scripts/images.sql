-- liquibase formatted sql

-- changeset aalekseev:1

CREATE TABLE IF NOT EXISTS image
(
    id         BIGSERIAL PRIMARY KEY,
    image      bytea   NOT NULL,
    file_size  integer NOT NULL,
    media_type text
);

-- changeset aalekseev:2
ALTER TABLE ads
    ADD COLUMN image_id integer REFERENCES image (id);

--changeset bbazarov:1
ALTER TABLE image
ADD COLUMN ads_id integer REFERENCES ads (id);

--changeset bbazarov:2
ALTER TABLE image
ADD COLUMN file_path VARCHAR NOT NULL;