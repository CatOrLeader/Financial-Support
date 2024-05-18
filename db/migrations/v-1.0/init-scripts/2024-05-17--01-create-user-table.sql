--liquibase formatted sql

--changeset vladislav77777:1
CREATE TABLE User_ (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);
