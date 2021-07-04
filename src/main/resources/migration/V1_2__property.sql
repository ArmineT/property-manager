CREATE TABLE public.property
(
    id          bigserial    NOT NULL,
    name        varchar(255) NULL,
    country     varchar(255) NULL,
    city        varchar(255) NULL,
    number      int4         NULL,
    postal_code varchar(255) NULL,
    description varchar(255) NULL,
    lat         varchar(255) NULL,
    client_id   int8         NULL,
    created_at  timestamp    NOT NULL,
    updated_at  timestamp    NULL,
    removed     bool         NOT NULL DEFAULT false,
    removed_at  timestamp    NULL,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT FK_property_client FOREIGN KEY (client_id) REFERENCES client (id)
);