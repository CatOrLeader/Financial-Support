--liquibase formatted sql

--changeset vladislav77777:8
CREATE TABLE Cashback (
                          user_id BIGSERIAL NOT NULL,
                          bank_id BIGSERIAL NOT NULL,
                          category_id BIGSERIAL NOT NULL,
                          ratio DOUBLE PRECISION NOT NULL,
                          CONSTRAINT fk_cashback_user FOREIGN KEY (user_id) REFERENCES User_ (id),
                          CONSTRAINT fk_cashback_bank FOREIGN KEY (bank_id) REFERENCES Bank (id),
                          CONSTRAINT fk_cashback_category FOREIGN KEY (category_id) REFERENCES Category (category_id)
);
