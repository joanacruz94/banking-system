USE bankingSystem;

INSERT INTO roles(name) VALUES
('ROLE_ADMIN'),
('ROLE_ACCOUNTHOLDER'),
('ROLE_THIRDPARTY');

INSERT INTO user(name, email, password) VALUES
('Joana Marta da Cruz', 'joanamartadacruz@gmail.com', '$2y$12$LYaUB/6YFJR/I6txh5IMFO3EQsZgquhZozabl7sTKUY8hp4s./A5.'),
('Anabela Alves Rodrigues', 'anabela.a.eu@gmail.com', '$2y$12$uInnujsTCsOaZTsjYDaPr.BUss7YuqwzGgIR7Ru3q5eZZdTC2zmq2'),
('Ana Sofia Marta da Cruz', 'anasofia.cruz@gmail.com', '$2y$12$aOkW8rm90IomMwyq/Nx7vu4wW61z3Gfdfnn5na2bJsekMYW2bcYoK'),
('Catia Marta da Cruz', 'catiacruz@gmail.com', '$2y$12$GzfXK5kRohCrKloZ5CevW.LPwmOqaXRcCD6hd7iFw8A8hRzfuIQvK');

INSERT INTO admin(user_id) VALUES
(1);

INSERT INTO third_party(user_id, hashed_key) VALUE
(2, '$2y$12$uInnujsTCsOaZTsjYDaPr.BUss7YuqwzGgIR7Ru3q5eZZdTC2zmq2');

INSERT INTO account_holder(user_id, date_of_birth, city, country, house_number, street_address, zip_code) VALUES
(3, '1996-7-17', 'Braga', 'Portugal', 12, 'Praceta João Beltrão', '4715-292'),
(4, '1991-1-12', 'Porto', 'Portugal', 234, 'Avenida Menéres', '4450-189');

INSERT INTO user_roles(user_id, role_id) VALUES
(1, 1),
(2, 3),
(3, 2),
(4, 2);

INSERT INTO account(balance, currency, penalty_fee, status) VALUES
(1200.50, 'EUR', 40, 'ACTIVE'),
(5000.80, 'EUR', 40, 'ACTIVE'),
(30000, 'EUR', 40, 'ACTIVE'),
(15000, 'EUR', 40, 'ACTIVE');

INSERT INTO student_checking_account(secret_key, account_id) VALUES
('123456789', 1);

INSERT INTO regular_checking_account(secret_key, charge_fee, minimum_balance, monthly_maintenance_fee, account_id) VALUES
('987654321', '2020-7-17', 250, 12, 2);

INSERT INTO savings_account(credit_date, interest_rate, minimum_balance, secret_key, account_id) VALUES
('2020-7-17', 0.33, 300, '427593754', 3);

INSERT INTO credit_card_account(credit_limit, debit_date, interest_rate, account_id) VALUES
(30000, '2020-7-17', 0.16, 4);

INSERT INTO account_owner(account_id, owner_id) VALUES
(1, 3),
(2, 4),
(3, 3),
(3, 4),
(4, 4);

INSERT INTO transaction(amount, transaction_date, id_account_from, id_account_to) VALUES
(20, '2020-06-25T17:09:42.411', 1, 2),
(20, '2020-06-25T18:10:42.411', 2, 3),
(20, '2020-06-25T19:11:42.411', 4, 3),
(20, '2020-06-25T20:12:42.411', 1, 4);