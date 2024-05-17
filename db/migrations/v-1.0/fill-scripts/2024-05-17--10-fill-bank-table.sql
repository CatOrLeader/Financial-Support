--liquibase formatted sql

--changeset vladislav77777:10
INSERT INTO Bank (id, bank_name) VALUES
                                     ('123e4567-e89b-12d3-a456-426614174000', 'Bank A'),
                                     ('123e4567-e89b-12d3-a456-426614174001', 'Bank B');
