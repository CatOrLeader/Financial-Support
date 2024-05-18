--liquibase formatted sql

--changeset vladislav77777:11
INSERT INTO Category (category_id, category_name) VALUES
                                                      (1, 'Food'),
                                                      (2, 'Transport');
