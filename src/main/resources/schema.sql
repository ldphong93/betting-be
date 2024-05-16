DROP TABLE app_user IF EXISTS;

CREATE TABLE app_user (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(100),
    balance INTEGER
);

CREATE TABLE match (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    description VARCHAR(1000),
    bet_ratio VARCHAR(10),
    match_date DATE,
    creation_date DATE,
    created_by VARCHAR(20)
);

CREATE TABLE bet_detail (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    match_id INT,
    bet_info TEXT,
    result VARCHAR(10),
    is_finalized boolean
);
