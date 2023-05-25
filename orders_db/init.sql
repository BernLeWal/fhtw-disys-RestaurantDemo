CREATE DATABASE orders_db;
\c orders_db;

CREATE TABLE IF NOT EXISTS pans (
        id serial PRIMARY KEY,
        diameter INT NOT NULL,
        teflon INT NOT NULL,
        dirty INT NOT NULL
    );

INSERT INTO pans(diameter, teflon, dirty)
VALUES
    (30, 1, 0),
    (30, 0, 0),
    (20, 1, 0),
    (20, 0, 0),
    (40, 1, 0);
