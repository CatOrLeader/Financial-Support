--liquibase formatted sql

--changeset vladislav77777:1
CREATE TABLE User_ (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);
