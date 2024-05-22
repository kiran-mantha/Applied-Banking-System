-- * Users:

-- & User 1:
INSERT INTO public._user(
	user_id, email, first_name, last_name, password, role)
	VALUES (11, 'thanuaaradya@gmail.com', 'Kiran', 'Mantha', '147258', 'MANAGER');
-- & User 2:
INSERT INTO public._user(
	user_id, email, first_name, last_name, password, role)
	VALUES (12, 'vaishnavi@gmail.com', 'Vaishnavi', 'Hiremath', '1234', 'HELP_DESK');

-- * Customers:

-- & Customer 1:
INSERT INTO public.customer_details(
	customer_id, added_on, contact, updated_on, added_by, customer_email, customer_first_name, customer_last_name, customer_password, updated_by)
	VALUES (1, '2024-05-18 13:40:18.633643', 3692581470, '2024-05-18 13:40:18.633643', 'Kiran Mantha', 'suresh@gmail.com', 'Suresh', 'Mondal', '1234', 'Kiran Mantha');
-- & Customer 2:
INSERT INTO public.customer_details(
	customer_id, added_on, contact, updated_on, added_by, customer_email, customer_first_name, customer_last_name, customer_password, updated_by)
	VALUES (2, '2024-05-18 13:45:46.577635', 9638527410, '2024-05-18 13:45:46.577635', 'Vaishnavi Hiremath', 'moinak@gmail.com', 'Moinak', 'Mondal', '1234', 'Vaishnavi Hiremath');

-- * Accounts:

-- & Account 1:
INSERT INTO public.account(
	account_balance, account_id, account_number, opened_date, account_type)
	VALUES (2500, 1, 25814736, '2024-05-18 13:43:32.453463', 'SAVINGS');
-- & Account 2:
INSERT INTO public.account(
	account_balance, account_id, account_number, opened_date, account_type)
	VALUES (5630, 2, 36925847, '2024-05-18 13:45:46.577635', 'SAVINGS');


-- * ATM cards:

-- & Card 1:
INSERT INTO public.atm_card(
	card_id, card_holder, card_number, _month, _year, account_account_id, pin)
	VALUES (1, 'Suresh', 96325878, 12, 2028, 1, 3344);
-- & Card 2:
INSERT INTO public.atm_card(
	card_id, card_holder, card_number, _month, _year, account_account_id, pin)
	VALUES (2, 'Moinak', 94655946, 12, 2032, 2, 1122);