--liquibase formatted sql

--changeset vladislav77777:4
CREATE TABLE Deposit (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGSERIAL NOT NULL,
                         bank_id BIGSERIAL NOT NULL,
                         amount DECIMAL(19,2) NOT NULL,
                         CONSTRAINT fk_deposit_user FOREIGN KEY (user_id) REFERENCES User_ (id),
                         CONSTRAINT fk_deposit_bank FOREIGN KEY (bank_id) REFERENCES Bank (id)
);
