--liquibase formatted sql

--changeset vladislav77777:9
INSERT INTO "User" (id, name) VALUES
                                  (1, 'John Doe'),
                                  (2, 'Jane Smith');
