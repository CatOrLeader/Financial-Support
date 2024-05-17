--liquibase formatted sql

--changeset vladislav77777:12
INSERT INTO Deposit (id, user_id, bank_id, amount) VALUES
                                                       (1, 1, 2, 1000.00),
                                                       (2, 2, 1, 1500.00);
