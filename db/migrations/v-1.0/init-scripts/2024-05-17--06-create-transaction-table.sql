--liquibase formatted sql

--changeset vladislav77777:6
CREATE TABLE Transaction (
                             user_id UUID NOT NULL,
                             bank_id UUID NOT NULL,
                             category_id UUID NOT NULL,
                             amount DECIMAL(19,2) NOT NULL,
                             date TIMESTAMP NOT NULL,
                             CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES User_ (id),
                             CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES Category (category_id),
                             CONSTRAINT fk_transaction_bank FOREIGN KEY (bank_id) REFERENCES Bank (id)
);
