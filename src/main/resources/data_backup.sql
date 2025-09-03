-- -----------------------------
-- 1. Barrios / Neighborhoods
-- -----------------------------
INSERT INTO neighborhoods (name, street_name) VALUES
('Centro', 'Calle Mayor 1'),
('Norte', 'Avenida Norte 23'),
('Sur', 'Calle Sur 45'),
('Este', 'Avenida Este 12');

-- -----------------------------
-- 2. Usuarios / Users
-- -----------------------------
INSERT INTO users (email, password, role, created_at, updated_at) VALUES
('maria.garcia@example.com', '$2a$10$7QZ5w5x9LQm9p8Oqx2HaP.JyqIsdj7MkqZ3LQxD9RQyEO5L9W4uG6', 'ROLE_USER', NOW(), NOW()),
('juan.perez@example.com', '$2a$10$K7JjGp8YlW9jf5pUYzRzpeQK78nxP3a7yKbT0Mxxk3gR0cQxzwSxC', 'ROLE_USER', NOW(), NOW()),
('laura.martin@example.com', '$2a$10$Zm9Q5gZ9aP7TtKliZzHtze0sQW7vsE7aTk4GyRvsRmYTJ8JYKcHO2', 'ROLE_USER', NOW(), NOW()),
('carlos.lopez@example.com', '$2a$10$Wu.3Js6r5.1VZzpUwR6xU.4aDMEwq6IcqE12z3FQxjTpVv6cN38QK', 'ROLE_USER', NOW(), NOW()),
('ana.sanchez@example.com', '$2a$10$UjRZtKq5L3HzjWy9H0F6FO8Xv0bspLNhqIoOGv5ew3uE7B6oW3xNu', 'ROLE_USER', NOW(), NOW());

-- -----------------------------
-- 3. Familias / Families
-- -----------------------------
INSERT INTO families (user_id, neighborhood_id, area, family_name, representative_name, profile_picture_url, description, created_at, updated_at) VALUES
(1, 1, 'Centro', 'Familia Garcia', 'Maria Garcia', NULL, 'Familia de ejemplo', NOW(), NOW()),
(2, 2, 'Norte', 'Familia Perez', 'Juan Perez', NULL, 'Familia de ejemplo', NOW(), NOW()),
(3, 3, 'Sur', 'Familia Martin', 'Laura Martin', NULL, 'Familia de ejemplo', NOW(), NOW()),
(4, 4, 'Este', 'Familia Lopez', 'Carlos Lopez', NULL, 'Familia de ejemplo', NOW(), NOW()),
(5, 1, 'Centro', 'Familia Sanchez', 'Ana Sanchez', NULL, 'Familia de ejemplo', NOW(), NOW());

-- -----------------------------
-- 4. Hijos / Children
-- -----------------------------
INSERT INTO children (family_id, gender, birth_date, created_at, updated_at) VALUES
(1, 'FEMALE', '2018-06-15', NOW(), NOW()),
(1, 'MALE', '2016-09-20', NOW(), NOW()),
(2, 'FEMALE', '2015-12-05', NOW(), NOW()),
(3, 'MALE', '2017-03-11', NOW(), NOW()),
(4, 'FEMALE', '2014-08-25', NOW(), NOW()),
(5, 'MALE', '2019-01-30', NOW(), NOW());






