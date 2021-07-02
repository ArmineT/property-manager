CREATE TABLE public.clients
(
    id         bigserial NOT NULL,
    username   varchar(255) NULL unique,
    "password" varchar(255) NULL,
    name       varchar(255) NULL,
    surname    varchar(255) NULL,
    created_at timestamp NOT NULL,
    removed    bool      NOT NULL DEFAULT false,
    removed_at timestamp NULL,
    updated_at timestamp NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
);