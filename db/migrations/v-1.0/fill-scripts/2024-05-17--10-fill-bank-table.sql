--liquibase formatted sql

--changeset vladislav77777:10
INSERT INTO Bank (id, bank_name) VALUES
                                     (1, 'Tinkoff'),
                                     (2, 'Sberbank');
