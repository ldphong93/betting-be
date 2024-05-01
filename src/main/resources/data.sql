INSERT INTO app_user (username, password, role, balance) VALUES
('admin1', 'admin@123', 'admin', 1000),
('admin2', 'admin@456', 'admin', 1000),
('roger_federer', 'roger@123', 'bettor', 200),
('rafael_nadal', 'rafa@123', 'bettor', 200),
('novak_djokovic', 'nole@123', 'bettor', 200),
('andy_murray', 'andy@123', 'bettor', 200);

INSERT INTO match (title, match_date, creation_date, description, bet_ratio, created_by) VALUES
('Arsenal - ManCity', CURRENT_DATE() + ((8 - DAY_OF_WEEK(CURRENT_DATE()) + 7) % 7), CURRENT_DATE, 'Super Sunday to define the champion', '1:1', 'SYSTEM'),
('MU - Liverpool', CURRENT_DATE() + ((7 - DAY_OF_WEEK(CURRENT_DATE()) + 7) % 7), CURRENT_DATE, 'Biggest England rivals match', '1:2', 'SYSTEM');
