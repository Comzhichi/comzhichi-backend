-- Agrega mas cursos (con prerequisitos) y secciones para probar validacion.
-- Ejecutar despues de datos_prueba_matriculas.sql

BEGIN;

-- Cursos adicionales
INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Avanzado B2', 'Inglés', 'B2', 'Curso de inglés nivel avanzado B2',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Intermedio B1')
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Inglés Avanzado B2');

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Inglés Conversacional', 'Inglés', 'A2', 'Curso de conversación en inglés',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Inglés Pre-Intermedio A2')
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Inglés Conversacional');

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Francés Intermedio A2', 'Francés', 'A2', 'Curso de francés nivel intermedio A2',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       (SELECT id FROM courses WHERE name = 'Francés Básico A1')
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Francés Intermedio A2');

INSERT INTO courses (name, language, level, description, coordinator_id, prerequisite_id)
SELECT 'Portugués Básico A1', 'Portugués', 'A1', 'Curso de portugués nivel básico A1',
       (SELECT id FROM coordinators WHERE user_id = (SELECT id FROM users WHERE email='coordinador@comzhichi.com')),
       NULL
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE name = 'Portugués Básico A1');

-- Secciones adicionales
INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'B2-G1', 'MONDAY,WEDNESDAY', '16:00', '18:00', 'B-202', 5, 5,
       (SELECT id FROM courses WHERE name = 'Inglés Avanzado B2'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'B2-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'CONV-G1', 'FRIDAY', '10:00', '12:00', 'A-105', 8, 8,
       (SELECT id FROM courses WHERE name = 'Inglés Conversacional'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'CONV-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'FR-A2-G1', 'MONDAY,WEDNESDAY', '12:00', '14:00', 'C-302', 6, 6,
       (SELECT id FROM courses WHERE name = 'Francés Intermedio A2'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'FR-A2-G1');

INSERT INTO sections (group_code, day_of_week, start_time, end_time, classroom,
                      total_vacancies, available_vacancies, course_id, teacher_id)
SELECT 'PT-A1-G1', 'SATURDAY', '09:00', '11:00', 'D-401', 10, 10,
       (SELECT id FROM courses WHERE name = 'Portugués Básico A1'),
       (SELECT id FROM teachers WHERE user_id = (SELECT id FROM users WHERE email='docente@comzhichi.com'))
WHERE NOT EXISTS (SELECT 1 FROM sections WHERE group_code = 'PT-A1-G1');

COMMIT;
