INSERT INTO app_user (username, password, role, balance) VALUES
('Admin1', 'admin@123', 'admin', 1000),
('Admin2', 'admin@456', 'admin', 1000),
('Booker1', 'booker@456', 'booker', 1000),
('Roger_Federer', 'roger@123', 'bettor', 200),
('Rafael_Nadal', 'rafa@123', 'bettor', 200),
('Novak_Djokovic', 'nole@123', 'bettor', 200),
('Andy_Murray', 'andy@123', 'bettor', 200);

INSERT INTO match (title, match_date, creation_date, description, bet_ratio, created_by) VALUES
('Arsenal - ManCity', CURRENT_DATE() + ((8 - DAY_OF_WEEK(CURRENT_DATE()) + 7) % 7) + 7, CURRENT_DATE, 'Get ready for an electrifying showdown between Arsenal and Man City as they battle it out to determine this year champion of England, forging a fierce new rivalry between the two clubs. Do not miss this historic match that promises to define football greatness...', '1:1', 'SYSTEM'),
('M.U - Liverpool', CURRENT_DATE() + ((7 - DAY_OF_WEEK(CURRENT_DATE()) + 7) % 7), CURRENT_DATE, 'Experience an iconic clash of football titans as M.U and Liverpool go head-to-head, reigniting their historic rivalry in a quest to reclaim their former glory. Witness the battle unfold as these legendary clubs leave no stone unturned in their pursuit of greatness...', '1:2', 'SYSTEM'),
('Chelsea - Newcastle', CURRENT_DATE() + ((7 - DAY_OF_WEEK(CURRENT_DATE()) + 7) % 7), CURRENT_DATE, 'Prepare for a high-stakes encounter as Chelsea and Newcastle, two clubs fueled by significant financial backing, face off in a match where their lavish investments in top-tier players have yet to yield desired results. Do not miss this showdown as both teams strive to turn their considerable resources into on-field success...', '1:2', 'SYSTEM');
