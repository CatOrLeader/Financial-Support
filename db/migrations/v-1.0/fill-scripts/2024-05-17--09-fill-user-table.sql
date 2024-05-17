--liquibase formatted sql

--changeset vladislav77777:9
INSERT INTO "User" (id, name) VALUES
                                  ('550e8400-e29b-41d4-a716-446655440000', 'John Doe'),
                                  ('550e8400-e29b-41d4-a716-446655440001', 'Jane Smith');
