DROP TABLE app_user IF EXISTS;

CREATE TABLE app_user (
    id IDENTITY NOT NULL PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(100),
    balance INTEGER
);
