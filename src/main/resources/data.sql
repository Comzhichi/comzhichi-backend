-- ══════════════════════════════════════════════════════
--  COMZHICHI — datos de prueba
--  Contraseña de todos los usuarios: comzhichi123
-- ══════════════════════════════════════════════════════

-- ── USUARIOS ────────────────────────────────────────────
INSERT INTO users (full_name, email, password, enabled, role) VALUES
-- Coordinador
('Ana Coordinadora',    'ana.coord@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'COORDINATOR'),
-- Profesores
('Carlos Profesor',     'carlos.prof@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'TEACHER'),
('Maria Profesora',     'maria.prof@comzhichi.com',   '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'TEACHER'),
('Luis Profesor',        'luis.prof@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'TEACHER'),
-- Estudiantes
('Luis Estudiante',     'luis.est@comzhichi.com',     '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Sofia Estudiante',    'sofia.est@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Diego Estudiante',    'diego.est@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Valeria Estudiante',  'valeria.est@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Andres Estudiante',   'andres.est@comzhichi.com',   '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Camila Estudiante',   'camila.est@comzhichi.com',   '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Pedro Estudiante',    'pedro.est@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Lucia Estudiante',    'lucia.est@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Andrea Estudiante',   'andrea.est@comzhichi.com',   '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Jorge Estudiante',    'jorge.est@comzhichi.com',    '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Mariana Estudiante',  'mariana.est@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Nicolas Estudiante',  'nicolas.est@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Fernanda Estudiante', 'fernanda.est@comzhichi.com', '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Ricardo Estudiante',  'ricardo.est@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Alejandro Estudiante','alejandro.est@comzhichi.com','$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Beatriz Estudiante',  'beatriz.est@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Carolina Estudiante', 'carolina.est@comzhichi.com', '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
('Daniel Estudiante',   'daniel.est@comzhichi.com',   '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'STUDENT'),
-- Padres
('Roberto Padre',       'roberto.padre@comzhichi.com','$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'PARENT'),
('Carmen Madre',        'carmen.madre@comzhichi.com', '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'PARENT'),
('Jorge Padre',         'jorge.padre@comzhichi.com',  '$2a$10$65tfZ99F/H4rupSPFhqwaenWh7KQE4StRAa4WpxseXJG/IcKW6Ozi', true, 'PARENT')
    ON CONFLICT (email) DO NOTHING;

-- ── PERFILES ────────────────────────────────────────────
INSERT INTO coordinators (user_id, department)
SELECT id, 'Académico' FROM users WHERE email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO teachers (user_id, specialty, active)
SELECT id, 'Inglés', true FROM users WHERE email = 'carlos.prof@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO teachers (user_id, specialty, active)
SELECT id, 'Francés', true FROM users WHERE email = 'maria.prof@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO teachers (user_id, specialty, active)
SELECT id, 'Portugués', true FROM users WHERE email = 'luis.prof@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO students (user_id)
SELECT id FROM users WHERE email IN (
                                     'luis.est@comzhichi.com', 'sofia.est@comzhichi.com', 'diego.est@comzhichi.com',
                                     'valeria.est@comzhichi.com', 'andres.est@comzhichi.com', 'camila.est@comzhichi.com',
                                     'pedro.est@comzhichi.com', 'lucia.est@comzhichi.com',
                                     'andrea.est@comzhichi.com', 'jorge.est@comzhichi.com', 'mariana.est@comzhichi.com',
                                     'nicolas.est@comzhichi.com', 'fernanda.est@comzhichi.com', 'ricardo.est@comzhichi.com',
                                     'alejandro.est@comzhichi.com', 'beatriz.est@comzhichi.com', 'carolina.est@comzhichi.com',
                                     'daniel.est@comzhichi.com'
    ) ON CONFLICT DO NOTHING;

INSERT INTO parents (user_id, phone_contact)
SELECT id, '999000001' FROM users WHERE email = 'roberto.padre@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO parents (user_id, phone_contact)
SELECT id, '999000002' FROM users WHERE email = 'carmen.madre@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO parents (user_id, phone_contact)
SELECT id, '999000003' FROM users WHERE email = 'jorge.padre@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

-- ── VINCULACIÓN PADRES-ESTUDIANTES ──────────────────────
INSERT INTO student_parents (student_id, parent_id, relationship)
SELECT s.id, p.id, 'Padre'
FROM students s
         JOIN users us ON s.user_id = us.id
         CROSS JOIN parents p
         JOIN users up ON p.user_id = up.id
WHERE us.email = 'luis.est@comzhichi.com' AND up.email = 'roberto.padre@comzhichi.com'
    ON CONFLICT DO NOTHING;

INSERT INTO student_parents (student_id, parent_id, relationship)
SELECT s.id, p.id, 'Madre'
FROM students s
         JOIN users us ON s.user_id = us.id
         CROSS JOIN parents p
         JOIN users up ON p.user_id = up.id
WHERE us.email = 'sofia.est@comzhichi.com' AND up.email = 'carmen.madre@comzhichi.com'
    ON CONFLICT DO NOTHING;

INSERT INTO student_parents (student_id, parent_id, relationship)
SELECT s.id, p.id, 'Madre'
FROM students s
         JOIN users us ON s.user_id = us.id
         CROSS JOIN parents p
         JOIN users up ON p.user_id = up.id
WHERE us.email = 'diego.est@comzhichi.com' AND up.email = 'carmen.madre@comzhichi.com'
    ON CONFLICT DO NOTHING;

INSERT INTO student_parents (student_id, parent_id, relationship)
SELECT s.id, p.id, 'Padre'
FROM students s
         JOIN users us ON s.user_id = us.id
         CROSS JOIN parents p
         JOIN users up ON p.user_id = up.id
WHERE us.email = 'camila.est@comzhichi.com' AND up.email = 'jorge.padre@comzhichi.com'
    ON CONFLICT DO NOTHING;

-- ── CURSOS ──────────────────────────────────────────────
INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Inglés A1', 'Inglés', 'A1', 'Curso introductorio de inglés', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Inglés A2', 'Inglés', 'A2', 'Inglés elemental con bases comunicativas', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Inglés B1', 'Inglés', 'B1', 'Inglés intermedio con énfasis en conversación', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Francés A1', 'Francés', 'A1', 'Introducción al francés', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Francés A2', 'Francés', 'A2', 'Francés elemental con bases comunicativas', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO courses (name, language, level, description, coordinator_id)
SELECT 'Portugués A1', 'Portugués', 'A1', 'Introducción al portugués', c.id
FROM coordinators c JOIN users u ON c.user_id = u.id
WHERE u.email = 'ana.coord@comzhichi.com' LIMIT 1
ON CONFLICT DO NOTHING;

-- ── PREREQUISITOS ────────────────────────────────────────
UPDATE courses SET prerequisite_id = (SELECT id FROM courses WHERE name = 'Inglés A1' ORDER BY id ASC LIMIT 1)
WHERE name = 'Inglés A2';

UPDATE courses SET prerequisite_id = (SELECT id FROM courses WHERE name = 'Inglés A2' ORDER BY id ASC LIMIT 1)
WHERE name = 'Inglés B1';

UPDATE courses SET prerequisite_id = (SELECT id FROM courses WHERE name = 'Francés A1' ORDER BY id ASC LIMIT 1)
WHERE name = 'Francés A2';

-- ── SECCIONES ───────────────────────────────────────────
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'ING-A1-A', 'MONDAY,WEDNESDAY', '08:00', '10:00', 'Aula 101', 20, 13,
       (SELECT id FROM courses WHERE name = 'Inglés A1' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'carlos.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'ING-A1-B', 'TUESDAY,THURSDAY', '18:00', '20:00', 'Aula 102', 20, 13,
       (SELECT id FROM courses WHERE name = 'Inglés A1' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'carlos.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'ING-A2-A', 'MONDAY,WEDNESDAY', '10:00', '12:00', 'Aula 101', 20, 18,
       (SELECT id FROM courses WHERE name = 'Inglés A2' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'carlos.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'ING-B1-A', 'FRIDAY', '14:00', '17:00', 'Aula 103', 15, 15,
       (SELECT id FROM courses WHERE name = 'Inglés B1' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'carlos.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'FRN-A1-A', 'TUESDAY,THURSDAY', '08:00', '10:00', 'Aula 201', 20, 12,
       (SELECT id FROM courses WHERE name = 'Francés A1' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'maria.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'FRN-A2-A', 'MONDAY,WEDNESDAY', '14:00', '16:00', 'Aula 202', 20, 20,
       (SELECT id FROM courses WHERE name = 'Francés A2' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'maria.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom, total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'POR-A1-A', 'SATURDAY', '09:00', '12:00', 'Aula 301', 15, 8,
       (SELECT id FROM courses WHERE name = 'Portugués A1' LIMIT 1),
       (SELECT t.id FROM teachers t JOIN users u ON t.user_id = u.id WHERE u.email = 'luis.prof@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

-- ── MATRÍCULAS ──────────────────────────────────────────
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'andres.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'camila.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'pedro.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'lucia.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-03-01', 'APPROVED',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'luis.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'luis.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A2-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-03-01', 'APPROVED',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'sofia.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'sofia.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A2-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-03-01', 'FAILED',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'diego.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'DROPPED',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'valeria.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT '2026-08-01', 'ACTIVE',
       (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'valeria.est@comzhichi.com' LIMIT 1),
       (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1)
ON CONFLICT DO NOTHING;

-- MATRICULAS ADICIONALES (estudiantes nuevos)
-- ING-A1-A (7 activos: 4 originales + 3 nuevos)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'andrea.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'jorge.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'mariana.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
-- ING-A1-B (7 activos: nuevos disjuntos)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'nicolas.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'fernanda.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'ricardo.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'alejandro.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'beatriz.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'carolina.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'daniel.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'ING-A1-B' LIMIT 1) ON CONFLICT DO NOTHING;
-- FRN-A1-A (7 activos)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'andrea.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'jorge.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'mariana.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'nicolas.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'fernanda.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'alejandro.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'beatriz.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'FRN-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
-- POR-A1-A (7 activos)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'carolina.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'daniel.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'andres.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'camila.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'pedro.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'lucia.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;
INSERT INTO enrollments (enrollment_date, status, student_id, section_id) SELECT '2026-08-01', 'ACTIVE', (SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = 'ricardo.est@comzhichi.com' LIMIT 1), (SELECT id FROM sections WHERE group_code = 'POR-A1-A' LIMIT 1) ON CONFLICT DO NOTHING;

-- ── ASISTENCIAS ──────────────────────────────────────────
INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT '2026-08-05', 'PRESENT', '', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'andres.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT '2026-08-07', 'PRESENT', '', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'andres.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT '2026-08-12', 'ABSENT', 'No avisó', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'andres.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT '2026-08-05', 'PRESENT', '', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'camila.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT '2026-08-07', 'LATE', 'Llegó 10 min tarde', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'camila.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

-- ── EVALUACIONES ─────────────────────────────────────────
INSERT INTO evaluations (type, date, score, feedback, enrollment_id)
SELECT 'PARTIAL', '2026-08-20', 78, 'Buen manejo del vocabulario básico', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'andres.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO evaluations (type, date, score, feedback, enrollment_id)
SELECT 'PARTIAL', '2026-08-20', 85, 'Excelente participación en clase', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'camila.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO evaluations (type, date, score, feedback, enrollment_id)
SELECT 'PARTIAL', '2026-08-20', 62, 'Necesita reforzar gramática', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'pedro.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

-- ── OBSERVACIONES ────────────────────────────────────────
INSERT INTO academic_observations (date, observation, enrollment_id)
SELECT '2026-08-10', 'Estudiante muy participativo, destaca en pronunciación', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'andres.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

INSERT INTO academic_observations (date, observation, enrollment_id)
SELECT '2026-08-10', 'Dificultades con los tiempos verbales, se recomienda práctica adicional', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'pedro.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'ACTIVE'
    ON CONFLICT DO NOTHING;

-- ── EVALUACIONES DE FIN DE NIVEL ─────────────────────────
INSERT INTO level_evaluations (result, date, comments, enrollment_id)
SELECT 'APPROVED', '2026-07-15', 'Excelente desempeño durante todo el ciclo', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'luis.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'APPROVED'
    ON CONFLICT DO NOTHING;

INSERT INTO level_evaluations (result, date, comments, enrollment_id)
SELECT 'APPROVED', '2026-07-15', 'Buen desempeño, lista para el siguiente nivel', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'sofia.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'APPROVED'
    ON CONFLICT DO NOTHING;

INSERT INTO level_evaluations (result, date, comments, enrollment_id)
SELECT 'FAILED', '2026-07-15', 'No alcanzó el puntaje mínimo requerido', e.id
FROM enrollments e
         JOIN students s ON e.student_id = s.id
         JOIN users u ON s.user_id = u.id
         JOIN sections sec ON e.section_id = sec.id
WHERE u.email = 'diego.est@comzhichi.com' AND sec.group_code = 'ING-A1-A' AND e.status = 'FAILED'
    ON CONFLICT DO NOTHING;

-- ── NOTIFICACIONES ───────────────────────────────────────
INSERT INTO notifications (message, read, created_at, user_id)
SELECT 'Resultado de evaluación de nivel: APPROVED', false, '2026-07-15 10:00:00',
       (SELECT id FROM users WHERE email = 'luis.est@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO notifications (message, read, created_at, user_id)
SELECT 'Resultado académico de su familiar: APPROVED', false, '2026-07-15 10:00:00',
       (SELECT id FROM users WHERE email = 'roberto.padre@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO notifications (message, read, created_at, user_id)
SELECT 'Resultado de evaluación de nivel: APPROVED', false, '2026-07-15 10:00:00',
       (SELECT id FROM users WHERE email = 'sofia.est@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO notifications (message, read, created_at, user_id)
SELECT 'Resultado académico de su familiar: APPROVED', false, '2026-07-15 10:00:00',
       (SELECT id FROM users WHERE email = 'carmen.madre@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO notifications (message, read, created_at, user_id)
SELECT 'Resultado de evaluación de nivel: FAILED', false, '2026-07-15 10:00:00',
       (SELECT id FROM users WHERE email = 'diego.est@comzhichi.com' LIMIT 1)
ON CONFLICT DO NOTHING;

-- ── RESETEO DE SECUENCIAS (EVITA ERRORES EN INSERT DEL FRONTEND) ─────────
SELECT setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id), 1)) FROM users;
SELECT setval(pg_get_serial_sequence('students', 'id'), coalesce(max(id), 1)) FROM students;
SELECT setval(pg_get_serial_sequence('teachers', 'id'), coalesce(max(id), 1)) FROM teachers;
SELECT setval(pg_get_serial_sequence('coordinators', 'id'), coalesce(max(id), 1)) FROM coordinators;
SELECT setval(pg_get_serial_sequence('courses', 'id'), coalesce(max(id), 1)) FROM courses;
