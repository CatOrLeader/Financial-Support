--liquibase formatted sql

--changeset vladislav77777:7
CREATE TABLE PiggyBank (
                           user_id UUID NOT NULL,
                           amount DECIMAL(19,2) NOT NULL,
                           goal VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_piggybank_user FOREIGN KEY (user_id) REFERENCES User_ (id)
);
