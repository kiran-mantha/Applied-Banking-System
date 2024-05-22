-- * ATM Card

DROP TABLE IF EXISTS _user;

 CREATE TABLE IF NOT EXISTS public._user
 (
     user_id integer PRIMARY KEY,
     email character varying(255),
     first_name character varying(255),
     last_name character varying(255),
     password character varying(255),
     role character varying(255)
 );


DROP TABLE IF EXISTS ACCOUNT CASCADE;

CREATE TABLE IF NOT EXISTS public.account
(
    account_balance double precision,
    account_id integer PRIMARY KEY,
    account_number integer,
    opened_date timestamp(6) without time zone,
    account_type character varying(255),
    customer_customer_id integer
);

DROP TABLE IF EXISTS ATM_CARD;

CREATE TABLE IF NOT EXISTS public.atm_card
(
    card_id integer PRIMARY KEY,
    card_holder character varying(255),
    card_number integer NOT NULL,
    _month integer NOT NULL,
    _year integer NOT NULL,
    account_account_id integer,
    pin integer NOT NULL
);

DROP TABLE IF EXISTS CUSTOMER_DETAILS;

CREATE TABLE IF NOT EXISTS public.customer_details
(
    customer_id integer PRIMARY KEY,
    added_on timestamp(6) without time zone,
    contact bigint NOT NULL,
    updated_on timestamp(6) without time zone,
    added_by character varying(255),
    customer_email character varying(255),
    customer_first_name character varying(255),
    customer_last_name character varying(255),
    customer_password character varying(255),
    updated_by character varying(255)
);