--liquibase formatted sql

--changeset vladislav77777:5
CREATE TABLE Budget (
                        user_id BIGSERIAL NOT NULL,
                        date_from TIMESTAMP NOT NULL,
                        date_to TIMESTAMP NOT NULL,
                        category_id BIGSERIAL NOT NULL,
                        bank_id BIGSERIAL NOT NULL,
                        threshold DECIMAL(19,2) NOT NULL,
                        CONSTRAINT fk_budget_user FOREIGN KEY (user_id) REFERENCES User_ (id),
                        CONSTRAINT fk_budget_category FOREIGN KEY (category_id) REFERENCES Category (category_id),
                        CONSTRAINT fk_budget_bank FOREIGN KEY (bank_id) REFERENCES Bank (id)
);
