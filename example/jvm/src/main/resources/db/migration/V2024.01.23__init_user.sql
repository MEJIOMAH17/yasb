CREATE TABLE public.users
(
    id       uuid NOT NULL PRIMARY KEY,
    password text NOT NULL,
    username text NOT NULL
);