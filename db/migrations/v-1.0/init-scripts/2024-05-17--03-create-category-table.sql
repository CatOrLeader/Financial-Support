--liquibase formatted sql

--changeset vladislav77777:3
CREATE TABLE Category (
                          category_id BIGSERIAL PRIMARY KEY,
                          category_name VARCHAR(255) NOT NULL
);
