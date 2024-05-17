--liquibase formatted sql

--changeset vladislav77777:12
INSERT INTO Deposit (id, user_id, bank_id, amount) VALUES
                                                       ('323e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '123e4567-e89b-12d3-a456-426614174000', 1000.00),
                                                       ('323e4567-e89b-12d3-a456-426614174001', '550e8400-e29b-41d4-a716-446655440001', '123e4567-e89b-12d3-a456-426614174001', 1500.00);
