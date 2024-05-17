--liquibase formatted sql

--changeset vladislav77777:2
CREATE TABLE Bank (
                      id UUID PRIMARY KEY,
                      bank_name VARCHAR(255) NOT NULL
);
