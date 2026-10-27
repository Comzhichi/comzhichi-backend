-- ============================================================
-- Datos de prueba: SOLO 4 usuarios (uno por perfil) + matriculas
-- Base de datos: PostgreSQL / Supabase (comzhichi-backend)
-- ============================================================
-- ADVERTENCIA: este script BORRA los datos existentes de usuarios,
-- perfiles, cursos, secciones y matriculas, para dejar un entorno
-- de pruebas limpio con exactamente 4 usuarios.
-- ============================================================

BEGIN;

-- 0. Limpieza completa (orden por FKs)
DELETE FROM notifications;
DELETE FROM academic_observations;
DELETE FROM attendances;
DELETE FROM evaluations;
DELETE FROM level_evaluations;
DELETE FROM enrollments;
DELETE FROM student_parents;
DELETE FROM sections;
DELETE FROM courses;
DELETE FROM students;
DELETE FROM parents;
DELETE FROM teachers;
DELETE FROM coordinators;
DELETE FROM users;

-- 1. Usuarios (4 perfiles)
INSERT INTO users (full_name, email, password, enabled, role) VALUES
('Coordinador General', 'coordinador@comzhichi.com', '$2a$10$j8I6qmhaTQ1gKU2SfBPIl.3RUuhZllV9X6R36ioTeq8uAPmjNrviG', TRUE, 'COORDINATOR'),
('Docente Principal',  'docente@comzhichi.com',      '$2a$10$V.S97HxZ55mKKORwYMuetu8dDBaDwmIQ.6kKYlTMx6JRl/OjyXSei', TRUE, 'TEACHER'),
('Estudiante Demo',    'estudiante@comzhichi.com',    '$2a$10$3qaOaO9msaNDZ8ka9tNZ1uyW1kaHRGFz2JlMhtR.qVHr73/ncuLIC', TRUE, 'STUDENT'),
('Padre Demo',         'padre@comzhichi.com',        '$2a$10$j9S0vY69bdNzeaBSL0A3jez0caOj7SoFEiWz/egVtquzIOTkwDUc6', TRUE, 'PARENT');

-- 2. Perfiles
INSERT INTO coordinators (user_id, department)
SELECT id, 'Idiomas' FROM users WHERE email = 'coordinador@comzhichi.com';

INSERT INTO teachers (user_id, specialty)
SELECT id, 'Inglés' FROM users WHERE email = 'docente@comzhichi.com';

INSERT INTO students (user_id)
SELECT id FROM users WHERE email = 'estudiante@comzhichi.com';

INSERT INTO parents (user_id, phone_contact)
SELECT id, '+51 999 888 777' FROM users WHERE email = 'padre@comzhichi.com';

-- Apoderado vinculado al estudiante (puede matricular)
INSERT INTO student_parents (student_id, parent_id, relationship, can_enroll)
SELECT
  (SELECT id FROM students WHERE user_id = (SELECT id FROM users WHERE email = 'estudiante@comzhichi.com')),
  (SELECT id FROM parents  WHERE user_id = (SELECT id FROM users WHERE email = 'padre@comzhichi.com')),
  'PADRE', TRUE;

-- 3. Cursos (cadena de prerequisitos)
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Básico A1', 'Inglés', 'A1', 'Curso de inglés nivel básico A1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       NULL;

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Pre-Intermedio A2', 'Inglés', 'A2', 'Curso de inglés nivel pre-intermedio A2',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1');

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Intermedio B1', 'Inglés', 'B1', 'Curso de inglés nivel intermedio B1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Pre-Intermedio A2');

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Francés Básico A1', 'Francés', 'A1', 'Curso de francés nivel básico A1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       NULL;

-- 4. Secciones (vacantes, horarios)
-- A1-G1: 2 vacantes, 1 disponible (estudiante inscrito) -> prueba de vacante restante
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A1-G1', 'MONDAY,WEDNESDAY', '08:00', '10:00', 'A-101', 2, 1,
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- A1-G2: 2 vacantes, 0 disponibles -> prueba de "sin vacantes"
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A1-G2', 'MONDAY,WEDNESDAY', '10:00', '12:00', 'A-102', 2, 0,
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- A2-G1: prerequisito A1; mismo horario que Francés para probar cruce de horarios
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A2-G1', 'TUESDAY,THURSDAY', '09:00', '11:00', 'A-103', 5, 5,
       (SELECT id FROM courses WHERE name = 'Inglés Pre-Intermedio A2'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- B1-G1: prerequisito A2 (no cumplido) -> prueba de prerequisito
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'B1-G1', 'MONDAY,WEDNESDAY', '14:00', '16:00', 'B-201', 5, 5,
       (SELECT id FROM courses WHERE name = 'Inglés Intermedio B1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- FR-A1-G1: mismo horario que A2-G1 para probar cruce de horarios
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'FR-A1-G1', 'TUESDAY,THURSDAY', '09:00', '11:00', 'C-301', 5, 4,
       (SELECT id FROM courses WHERE name = 'Francés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- A1-G3: solapa con A1-G1 (09:00-11:00 vs 08:00-10:00) para probar cruce
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A1-G3', 'MONDAY,WEDNESDAY', '09:00', '11:00', 'A-104', 5, 5,
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'));

-- 5. Matrículas del estudiante
-- A1-G1: APPROVED (cumple prerequisito para A2)
WITH stu AS (
  SELECT s.id AS student_id FROM students s JOIN users u ON u.id = s.user_id WHERE u.email = 'estudiante@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id FROM sections sc JOIN courses c ON c.id = sc.course_id WHERE sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-01-10', 'APPROVED', stu.student_id, sec.section_id FROM stu, sec;

-- Francés: ACTIVE con mismo horario que A2 -> al intentar matricular A2 debe fallar por cruce
WITH stu AS (
  SELECT s.id AS student_id FROM students s JOIN users u ON u.id = s.user_id WHERE u.email = 'estudiante@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id FROM sections sc JOIN courses c ON c.id = sc.course_id WHERE sc.group_code = 'FR-A1-G1' AND c.name = 'Francés Básico A1'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-01-15', 'ACTIVE', stu.student_id, sec.section_id FROM stu, sec;

-- 6. Detalle de la matrícula A1-G1 (asistencias, evaluaciones, nivel, observación)
WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e JOIN students s ON s.id = e.student_id JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'estudiante@comzhichi.com' AND sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT d::date, st, obs, enr.enrollment_id
FROM enr, (VALUES
  (DATE '2026-01-12', 'PRESENT', 'Asistencia normal'),
  (DATE '2026-01-14', 'LATE', 'Llegó 10 minutos tarde'),
  (DATE '2026-01-19', 'ABSENT', 'Falta justificada'),
  (DATE '2026-01-21', 'PRESENT', NULL)
) AS v(d, st, obs)
WHERE NOT EXISTS (SELECT 1 FROM attendances a WHERE a.enrollment_id = enr.enrollment_id AND a.date = v.d);

WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e JOIN students s ON s.id = e.student_id JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'estudiante@comzhichi.com' AND sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO evaluations (type, date, score, feedback, enrollment_id)
SELECT t, d::date, sc, fb, enr.enrollment_id
FROM enr, (VALUES
  ('PARTIAL', DATE '2026-01-30', 16, 'Buen desempeño en gramática'),
  ('FINAL',   DATE '2026-02-20', 18, 'Excelente nivel oral')
) AS v(t, d, sc, fb)
WHERE NOT EXISTS (SELECT 1 FROM evaluations ev WHERE ev.enrollment_id = enr.enrollment_id AND ev.type = v.t);

WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e JOIN students s ON s.id = e.student_id JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'estudiante@comzhichi.com' AND sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO level_evaluations (result, date, comments, enrollment_id)
SELECT 'APPROVED', DATE '2026-02-22', 'Aprueba nivel A1, puede avanzar a A2', enr.enrollment_id
FROM enr
WHERE NOT EXISTS (SELECT 1 FROM level_evaluations le WHERE le.enrollment_id = enr.enrollment_id);

WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e JOIN students s ON s.id = e.student_id JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'estudiante@comzhichi.com' AND sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO academic_observations (date, observation, enrollment_id)
SELECT DATE '2026-01-25', 'Estudiante muy participativa, buen progreso en comprensión auditiva.', enr.enrollment_id
FROM enr
WHERE NOT EXISTS (SELECT 1 FROM academic_observations ao WHERE ao.enrollment_id = enr.enrollment_id);

COMMIT;

-- Resumen
SELECT 'users' AS tabla, COUNT(*) AS registros FROM users
UNION ALL SELECT 'students', COUNT(*) FROM students
UNION ALL SELECT 'parents', COUNT(*) FROM parents
UNION ALL SELECT 'student_parents', COUNT(*) FROM student_parents
UNION ALL SELECT 'courses', COUNT(*) FROM courses
UNION ALL SELECT 'sections', COUNT(*) FROM sections
UNION ALL SELECT 'enrollments', COUNT(*) FROM enrollments;
