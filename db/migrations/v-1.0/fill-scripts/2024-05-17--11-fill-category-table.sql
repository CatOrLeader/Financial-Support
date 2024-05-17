--liquibase formatted sql

--changeset vladislav77777:11
INSERT INTO Category (category_id, category_name) VALUES
                                                      ('223e4567-e89b-12d3-a456-426614174000', 'Food'),
                                                      ('223e4567-e89b-12d3-a456-426614174001', 'Transport');
