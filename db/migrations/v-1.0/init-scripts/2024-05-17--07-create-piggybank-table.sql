--liquibase formatted sql

--changeset vladislav77777:7
CREATE TABLE piggy_bank (
                            id BIGSERIAL PRIMARY KEY,
                            user_id BIGSERIAL NOT NULL,
                           amount DECIMAL(19,2) NOT NULL,
                           goal VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_piggybank_user FOREIGN KEY (user_id) REFERENCES User_ (id)
);
