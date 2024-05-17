--liquibase formatted sql

--changeset vladislav77777:13
INSERT INTO Budget (user_id, date_from, date_to, category_id, bank_id, threshold) VALUES
                                                                                      (2, '2024-05-01 00:00:00', '2024-05-31 23:59:59', 2, 1, 500.00),
                                                                                      (1, '2024-05-01 00:00:00', '2024-05-31 23:59:59', 1, 2, 300.00);
