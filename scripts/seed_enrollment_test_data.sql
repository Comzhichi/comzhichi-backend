-- ============================================================
-- Datos de prueba para el MODULO DE MATRICULAS / SECCIONES
-- Base de datos: PostgreSQL / Supabase (comzhichi-backend)
-- ============================================================
-- Recomendado: ejecutar antes el script clean_sections_enrollments.sql
-- (o al menos tener las tablas vacias de matriculas/secciones).
-- Este script es idempotente: no duplica usuarios/cursos/secciones
-- si ya existen (se identifican por email/nombre/codigo de grupo).
-- ============================================================

BEGIN;

-- ============================================================
-- 1. Usuarios de prueba
-- ============================================================
INSERT INTO users (full_name, email, password, enabled, role)
SELECT 'María Coordinadora', 'maria.coord@comzhichi.com',
       '$2a$10$aZPPjm9VO8462nUuAuBR1O6hVTnWgEqcQd0fRHCViaZs/CyA/i5H.',
       TRUE, 'COORDINATOR'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'maria.coord@comzhichi.com');

INSERT INTO users (full_name, email, password, enabled, role)
SELECT 'Carlos Docente', 'carlos.teacher@comzhichi.com',
       '$2a$10$aZPPjm9VO8462nUuAuBR1O6hVTnWgEqcQd0fRHCViaZs/CyA/i5H.',
       TRUE, 'TEACHER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'carlos.teacher@comzhichi.com');

INSERT INTO users (full_name, email, password, enabled, role)
SELECT 'Ana Pérez', 'ana@comzhichi.com',
       '$2a$10$aZPPjm9VO8462nUuAuBR1O6hVTnWgEqcQd0fRHCViaZs/CyA/i5H.',
       TRUE, 'STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ana@comzhichi.com');

INSERT INTO users (full_name, email, password, enabled, role)
SELECT 'Luis Gómez', 'luis@comzhichi.com',
       '$2a$10$aZPPjm9VO8462nUuAuBR1O6hVTnWgEqcQd0fRHCViaZs/CyA/i5H.',
       TRUE, 'STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'luis@comzhichi.com');

INSERT INTO users (full_name, email, password, enabled, role)
SELECT 'Carla Ruiz', 'carla@comzhichi.com',
       '$2a$10$aZPPjm9VO8462nUuAuBR1O6hVTnWgEqcQd0fRHCViaZs/CyA/i5H.',
       TRUE, 'STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'carla@comzhichi.com');

-- ============================================================
-- 2. Perfiles (coordinator, teacher, students)
-- ============================================================
INSERT INTO coordinators (user_id, department)
SELECT u.id, 'Idiomas'
FROM users u
WHERE u.email = 'maria.coord@comzhichi.com'
  AND NOT EXISTS (SELECT 1 FROM coordinators c WHERE c.user_id = u.id);

INSERT INTO teachers (user_id, specialty)
SELECT u.id, 'Inglés'
FROM users u
WHERE u.email = 'carlos.teacher@comzhichi.com'
  AND NOT EXISTS (SELECT 1 FROM teachers t WHERE t.user_id = u.id);

INSERT INTO students (user_id)
SELECT u.id
FROM users u
WHERE u.email IN ('ana@comzhichi.com','luis@comzhichi.com','carla@comzhichi.com')
  AND NOT EXISTS (SELECT 1 FROM students s WHERE s.user_id = u.id);

-- ============================================================
-- 3. Cursos (con cadena de prerequisitos)
-- ============================================================
-- Curso base sin prerequisito
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Básico A1', 'Inglés', 'A1',
       'Curso de inglés nivel básico A1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='maria.coord@comzhichi.com')),
       NULL
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Inglés Básico A1');

-- Curso con prerequisito A1
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Pre-Intermedio A2', 'Inglés', 'A2',
       'Curso de inglés nivel pre-intermedio A2',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='maria.coord@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1')
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Inglés Pre-Intermedio A2');

-- Curso con prerequisito A2
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Intermedio B1', 'Inglés', 'B1',
       'Curso de inglés nivel intermedio B1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='maria.coord@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Pre-Intermedio A2')
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Inglés Intermedio B1');

-- Curso sin prerequisito (francés)
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Francés Básico A1', 'Francés', 'A1',
       'Curso de francés nivel básico A1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='maria.coord@comzhichi.com')),
       NULL
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Francés Básico A1');

-- ============================================================
-- 4. Secciones
-- ============================================================
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A1-G1', 'MONDAY,WEDNESDAY', '08:00', '10:00', 'A-101',
       30, 27,
       (SELECT id FROM courses WHERE name = 'Inglés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='carlos.teacher@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'A1-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'A2-G1', 'TUESDAY,THURSDAY', '09:00', '11:00', 'A-102',
       25, 23,
       (SELECT id FROM courses WHERE name = 'Inglés Pre-Intermedio A2'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='carlos.teacher@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'A2-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'B1-G1', 'MONDAY,WEDNESDAY', '14:00', '16:00', 'B-201',
       20, 20,
       (SELECT id FROM courses WHERE name = 'Inglés Intermedio B1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='carlos.teacher@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'B1-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'FR-A1-G1', 'FRIDAY', '16:00', '18:00', 'C-301',
       15, 15,
       (SELECT id FROM courses WHERE name = 'Francés Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='carlos.teacher@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'FR-A1-G1');

-- ============================================================
-- 5. Matrículas (enrollments)
-- ============================================================
-- Ana: A1 APPROVED (prerequisito cumplido) y A2 ACTIVE
WITH stu AS (
  SELECT s.id AS student_id
  FROM students s JOIN users u ON u.id = s.user_id
  WHERE u.email = 'ana@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id
  FROM sections sc JOIN courses c ON c.id = sc.course_id
  WHERE sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-01-10', 'APPROVED', stu.student_id, sec.section_id
FROM stu, sec
WHERE NOT EXISTS (
  SELECT 1 FROM enrollments e
  WHERE e.student_id = stu.student_id AND e.section_id = sec.section_id
);

WITH stu AS (
  SELECT s.id AS student_id
  FROM students s JOIN users u ON u.id = s.user_id
  WHERE u.email = 'ana@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id
  FROM sections sc JOIN courses c ON c.id = sc.course_id
  WHERE sc.group_code = 'A2-G1' AND c.name = 'Inglés Pre-Intermedio A2'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-02-01', 'ACTIVE', stu.student_id, sec.section_id
FROM stu, sec
WHERE NOT EXISTS (
  SELECT 1 FROM enrollments e
  WHERE e.student_id = stu.student_id AND e.section_id = sec.section_id
);

-- Luis: A1 ACTIVE (no aprobado -> no puede matricularse en A2, ideal para probar prerequisito)
WITH stu AS (
  SELECT s.id AS student_id
  FROM students s JOIN users u ON u.id = s.user_id
  WHERE u.email = 'luis@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id
  FROM sections sc JOIN courses c ON c.id = sc.course_id
  WHERE sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-01-12', 'ACTIVE', stu.student_id, sec.section_id
FROM stu, sec
WHERE NOT EXISTS (
  SELECT 1 FROM enrollments e
  WHERE e.student_id = stu.student_id AND e.section_id = sec.section_id
);

-- Carla: A1 APPROVED, A2 ACTIVE, y un registro DROPPED en A1 (otro grupo hipotético no existe, se usa mismo? mejor FAILED en A2? se omite)
WITH stu AS (
  SELECT s.id AS student_id
  FROM students s JOIN users u ON u.id = s.user_id
  WHERE u.email = 'carla@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id
  FROM sections sc JOIN courses c ON c.id = sc.course_id
  WHERE sc.group_code = 'A1-G1' AND c.name = 'Inglés Básico A1'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-01-08', 'APPROVED', stu.student_id, sec.section_id
FROM stu, sec
WHERE NOT EXISTS (
  SELECT 1 FROM enrollments e
  WHERE e.student_id = stu.student_id AND e.section_id = sec.section_id
);

WITH stu AS (
  SELECT s.id AS student_id
  FROM students s JOIN users u ON u.id = s.user_id
  WHERE u.email = 'carla@comzhichi.com'
), sec AS (
  SELECT sc.id AS section_id
  FROM sections sc JOIN courses c ON c.id = sc.course_id
  WHERE sc.group_code = 'A2-G1' AND c.name = 'Inglés Pre-Intermedio A2'
)
INSERT INTO enrollments (enrollment_date, status, student_id, section_id)
SELECT DATE '2026-02-03', 'ACTIVE', stu.student_id, sec.section_id
FROM stu, sec
WHERE NOT EXISTS (
  SELECT 1 FROM enrollments e
  WHERE e.student_id = stu.student_id AND e.section_id = sec.section_id
);

-- ============================================================
-- 6. Asistencias (para la matrícula de Ana en A1)
-- ============================================================
WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e
  JOIN students s ON s.id = e.student_id
  JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id
  JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'ana@comzhichi.com'
    AND sc.group_code = 'A1-G1'
    AND c.name = 'Inglés Básico A1'
)
INSERT INTO attendances (date, status, observation, enrollment_id)
SELECT d::date, st, obs, enr.enrollment_id
FROM enr,
     (VALUES
       (DATE '2026-01-12', 'PRESENT', 'Asistencia normal'),
       (DATE '2026-01-14', 'LATE', 'Llegó 10 minutos tarde'),
       (DATE '2026-01-19', 'ABSENT', 'Falta justificada por enfermedad'),
       (DATE '2026-01-21', 'PRESENT', NULL)
     ) AS v(d, st, obs)
WHERE NOT EXISTS (
  SELECT 1 FROM attendances a
  WHERE a.enrollment_id = enr.enrollment_id AND a.date = v.d
);

-- ============================================================
-- 7. Evaluaciones (para la matrícula de Ana en A1)
-- ============================================================
WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e
  JOIN students s ON s.id = e.student_id
  JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id
  JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'ana@comzhichi.com'
    AND sc.group_code = 'A1-G1'
    AND c.name = 'Inglés Básico A1'
)
INSERT INTO evaluations (type, date, score, feedback, enrollment_id)
SELECT t, d::date, sc, fb, enr.enrollment_id
FROM enr,
     (VALUES
       ('PARTIAL'::varchar, DATE '2026-01-30', 16, 'Buen desempeño en gramática'),
       ('FINAL'::varchar,   DATE '2026-02-20', 18, 'Excelente nivel oral')
     ) AS v(t, d, sc, fb)
WHERE NOT EXISTS (
  SELECT 1 FROM evaluations ev
  WHERE ev.enrollment_id = enr.enrollment_id AND ev.type = v.t
);

-- ============================================================
-- 8. Evaluación de nivel (para la matrícula de Ana en A1)
-- ============================================================
WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e
  JOIN students s ON s.id = e.student_id
  JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id
  JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'ana@comzhichi.com'
    AND sc.group_code = 'A1-G1'
    AND c.name = 'Inglés Básico A1'
)
INSERT INTO level_evaluations (result, date, comments, enrollment_id)
SELECT 'APPROVED', DATE '2026-02-22',
       'Aprueba nivel A1, puede avanzar a A2', enr.enrollment_id
FROM enr
WHERE NOT EXISTS (
  SELECT 1 FROM level_evaluations le WHERE le.enrollment_id = enr.enrollment_id
);

-- ============================================================
-- 9. Observación académica (para la matrícula de Ana en A1)
-- ============================================================
WITH enr AS (
  SELECT e.id AS enrollment_id
  FROM enrollments e
  JOIN students s ON s.id = e.student_id
  JOIN users u ON u.id = s.user_id
  JOIN sections sc ON sc.id = e.section_id
  JOIN courses c ON c.id = sc.course_id
  WHERE u.email = 'ana@comzhichi.com'
    AND sc.group_code = 'A1-G1'
    AND c.name = 'Inglés Básico A1'
)
INSERT INTO academic_observations (date, observation, enrollment_id)
SELECT DATE '2026-01-25',
       'Estudiante muy participativa, buen progreso en comprensión auditiva.',
       enr.enrollment_id
FROM enr
WHERE NOT EXISTS (
  SELECT 1 FROM academic_observations ao WHERE ao.enrollment_id = enr.enrollment_id
);

COMMIT;

-- ============================================================
-- Resumen para validar
-- ============================================================
SELECT 'users' AS tabla, COUNT(*) AS registros FROM users
UNION ALL SELECT 'students', COUNT(*) FROM students
UNION ALL SELECT 'teachers', COUNT(*) FROM teachers
UNION ALL SELECT 'coordinators', COUNT(*) FROM coordinators
UNION ALL SELECT 'courses', COUNT(*) FROM courses
UNION ALL SELECT 'sections', COUNT(*) FROM sections
UNION ALL SELECT 'enrollments', COUNT(*) FROM enrollments
UNION ALL SELECT 'attendances', COUNT(*) FROM attendances
UNION ALL SELECT 'evaluations', COUNT(*) FROM evaluations
UNION ALL SELECT 'level_evaluations', COUNT(*) FROM level_evaluations
UNION ALL SELECT 'academic_observations', COUNT(*) FROM academic_observations;
