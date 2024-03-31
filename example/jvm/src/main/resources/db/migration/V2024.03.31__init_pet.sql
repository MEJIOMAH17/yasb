CREATE TABLE public.pets
(
    id       uuid NOT NULL,
    owner    uuid NOT NULL REFERENCES public.users(id) ,
    name     text NOT NULL
);