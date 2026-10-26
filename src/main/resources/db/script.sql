INSERT INTO users (name, email, password, enabled, role)
VALUES
    (
        'ana.estudiante',
        'ana.martinez@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'STUDENT'
    ),
    (
        'bruno.estudiante',
        'bruno.rojas@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'STUDENT'
    ),
    (
        'carla.estudiante',
        'carla.silva@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'STUDENT'
    ),
    (
        'diego.docente',
        'diego.fuentes@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'TEACHER'
    ),
    (
        'elena.docente',
        'elena.vargas@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'TEACHER'
    ),
    (
        'fernando.coordinador',
        'fernando.navarro@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'COORDINATOR'
    ),
    (
        'gabriela.coordinadora',
        'gabriela.morales@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'COORDINATOR'
    ),
    (
        'patricia.apoderada',
        'patricia.martinez@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'PARENT'
    ),
    (
        'roberto.apoderado',
        'roberto.rojas@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'PARENT'
    ),
    (
        'sofia.apoderada',
        'sofia.silva@comzhichi.test',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        TRUE,
        'PARENT'
    );

INSERT INTO students (user_id, full_name, level, institution)
SELECT
    u.id,
    datos.full_name,
    datos.level,
    datos.institution
FROM (
         VALUES
             (
                 'ana.estudiante',
                 'Ana Martínez González',
                 'Intermedio',
                 'Colegio Bicentenario de Santiago'
             ),
             (
                 'bruno.estudiante',
                 'Bruno Rojas Pérez',
                 'Básico',
                 'Liceo Técnico Central'
             ),
             (
                 'carla.estudiante',
                 'Carla Silva Fernández',
                 'Avanzado',
                 'Instituto Educacional Los Andes'
             )
     ) AS datos(username, full_name, level, institution)
         JOIN users u ON u.name = datos.username;

INSERT INTO teachers (user_id, specialty)
SELECT
    u.id,
    datos.specialty
FROM (
         VALUES
             (
                 'diego.docente',
                 'Inglés comunicacional y gramática'
             ),
             (
                 'elena.docente',
                 'Matemática y razonamiento lógico'
             )
     ) AS datos(username, specialty)
         JOIN users u ON u.name = datos.username;

INSERT INTO coordinators (user_id, department)
SELECT
    u.id,
    datos.department
FROM (
         VALUES
             (
                 'fernando.coordinador',
                 'Coordinación Académica'
             ),
             (
                 'gabriela.coordinadora',
                 'Gestión Curricular'
             )
     ) AS datos(username, department)
         JOIN users u ON u.name = datos.username;

INSERT INTO parents (user_id, phone_contact)
SELECT
    u.id,
    datos.phone_contact
FROM (
         VALUES
             (
                 'patricia.apoderada',
                 '+56987654321'
             ),
             (
                 'roberto.apoderado',
                 '+56976543210'
             ),
             (
                 'sofia.apoderada',
                 '+56965432109'
             )
     ) AS datos(username, phone_contact)
         JOIN users u ON u.name = datos.username;

INSERT INTO student_parents (student_id, parent_id, relationship, can_enroll)
SELECT
    s.id,
    p.id,
    datos.relationship,
    datos.can_enroll
FROM (
         VALUES
             (
                 'ana.estudiante',
                 'patricia.apoderada',
                 'MADRE',
                 TRUE
             ),
             (
                 'bruno.estudiante',
                 'roberto.apoderado',
                 'PADRE',
                 TRUE
             ),
             (
                 'carla.estudiante',
                 'sofia.apoderada',
                 'MADRE',
                 TRUE
             )
     ) AS datos(student_username, parent_username, relationship, can_enroll)
         JOIN users su ON su.name = datos.student_username
         JOIN students s ON s.user_id = su.id
         JOIN users pu ON pu.name = datos.parent_username
         JOIN parents p ON p.user_id = pu.id;

INSERT INTO courses (name, level, description, coordinator_id)
SELECT
    datos.name,
    datos.level,
    datos.description,
    c.id
FROM (
         VALUES
             (
                 'Inglés Comunicacional',
                 'Básico',
                 'Curso orientado al desarrollo de comprensión auditiva, expresión oral y vocabulario en inglés.',
                 'fernando.coordinador'
             ),
             (
                 'Matemática Aplicada',
                 'Intermedio',
                 'Curso de razonamiento lógico, resolución de problemas y aplicación de conceptos matemáticos.',
                 'fernando.coordinador'
             ),
             (
                 'Comunicación Efectiva',
                 'Avanzado',
                 'Curso enfocado en comprensión lectora, escritura formal y comunicación académica.',
                 'gabriela.coordinadora'
             ),
             (
                 'Preparación TOEFL Junior',
                 'Avanzado',
                 'Curso de preparación para evaluaciones internacionales de comprensión y uso del idioma inglés.',
                 'gabriela.coordinadora'
             )
     ) AS datos(name, level, description, coordinator_username)
         JOIN users u ON u.name = datos.coordinator_username
         JOIN coordinators c ON c.user_id = u.id;

INSERT INTO sections (
    group_code,
    schedule,
    classroom,
    total_vacancies,
    available_vacancies,
    course_id,
    teacher_id
)
SELECT
    datos.group_code,
    datos.schedule,
    datos.classroom,
    datos.total_vacancies,
    datos.available_vacancies,
    c.id,
    t.id
FROM (
         VALUES
             (
                 'ING-BAS-A',
                 'Lunes y miércoles 16:00 - 17:30',
                 'Aula 101',
                 20,
                 18,
                 'Inglés Comunicacional',
                 'diego.docente'
             ),
             (
                 'ING-BAS-B',
                 'Jueves 16:00 - 17:30',
                 'Aula 102',
                 15,
                 14,
                 'Inglés Comunicacional',
                 'diego.docente'
             ),
             (
                 'MAT-INT-A',
                 'Martes y jueves 18:00 - 19:30',
                 'Aula 201',
                 25,
                 23,
                 'Matemática Aplicada',
                 'elena.docente'
             ),
             (
                 'COM-AVA-A',
                 'Miércoles y viernes 17:00 - 18:30',
                 'Aula 301',
                 20,
                 19,
                 'Comunicación Efectiva',
                 'elena.docente'
             ),
             (
                 'TOE-AVA-A',
                 'Sábado 10:00 - 12:00',
                 'Laboratorio de idiomas',
                 15,
                 15,
                 'Preparación TOEFL Junior',
                 'diego.docente'
             )
     ) AS datos(
                group_code,
                schedule,
                classroom,
                total_vacancies,
                available_vacancies,
                course_name,
                teacher_username
    )
         JOIN courses c ON c.name = datos.course_name
         JOIN users u ON u.name = datos.teacher_username
         JOIN teachers t ON t.user_id = u.id;

INSERT INTO enrollments (
    enrollment_date,
    status,
    student_id,
    section_id
)
SELECT
    datos.enrollment_date,
    datos.status,
    s.id,
    sec.id
FROM (
         VALUES
             (
                 DATE '2026-03-02',
                 'ACTIVA',
                 'ana.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-02',
                 'ACTIVA',
                 'ana.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-03',
                 'ACTIVA',
                 'bruno.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-03',
                 'ACTIVA',
                 'bruno.estudiante',
                 'COM-AVA-A'
             ),
             (
                 DATE '2026-03-04',
                 'ACTIVA',
                 'carla.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-04',
                 'ACTIVA',
                 'carla.estudiante',
                 'ING-BAS-B'
             ),
             (
                 DATE '2026-02-10',
                 'FINALIZADA',
                 'carla.estudiante',
                 'TOE-AVA-A'
             )
     ) AS datos(enrollment_date, status, student_username, group_code)
         JOIN users u ON u.name = datos.student_username
         JOIN students s ON s.user_id = u.id
         JOIN sections sec ON sec.group_code = datos.group_code;

INSERT INTO attendances (
    date,
    status,
    observation,
    enrollment_id
)
SELECT
    datos.date,
    datos.status,
    datos.observation,
    e.id
FROM (
         VALUES
             (
                 DATE '2026-03-09',
                 'PRESENTE',
                 'Participó activamente en las actividades de conversación.',
                 'ana.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-11',
                 'PRESENTE',
                 'Demostró buen manejo del vocabulario trabajado.',
                 'ana.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-10',
                 'AUSENTE',
                 'Inasistencia sin justificación registrada.',
                 'ana.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-12',
                 'JUSTIFICADA',
                 'Inasistencia justificada por motivo médico.',
                 'bruno.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-11',
                 'PRESENTE',
                 'Participó en la exposición grupal.',
                 'bruno.estudiante',
                 'COM-AVA-A'
             ),
             (
                 DATE '2026-03-10',
                 'PRESENTE',
                 'Resolvió correctamente los ejercicios de la sesión.',
                 'carla.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-12',
                 'PRESENTE',
                 'Presentó un nivel avanzado de comprensión oral.',
                 'carla.estudiante',
                 'ING-BAS-B'
             ),
             (
                 DATE '2026-02-14',
                 'PRESENTE',
                 'Completó la sesión de preparación TOEFL.',
                 'carla.estudiante',
                 'TOE-AVA-A'
             )
     ) AS datos(date, status, observation, student_username, group_code)
         JOIN users u ON u.name = datos.student_username
         JOIN students s ON s.user_id = u.id
         JOIN sections sec ON sec.group_code = datos.group_code
         JOIN enrollments e
              ON e.student_id = s.id
                  AND e.section_id = sec.id;

INSERT INTO evaluations (
    score,
    feedback,
    enrollment_id
)
SELECT
    datos.score,
    datos.feedback,
    e.id
FROM (
         VALUES
             (
                 88,
                 'Buen desempeño en comprensión auditiva y participación oral.',
                 'ana.estudiante',
                 'ING-BAS-A'
             ),
             (
                 76,
                 'Comprende los procedimientos, pero debe reforzar la resolución de problemas.',
                 'ana.estudiante',
                 'MAT-INT-A'
             ),
             (
                 67,
                 'Debe ampliar su vocabulario y practicar estructuras gramaticales básicas.',
                 'bruno.estudiante',
                 'ING-BAS-A'
             ),
             (
                 91,
                 'Excelente argumentación y claridad en la presentación escrita.',
                 'bruno.estudiante',
                 'COM-AVA-A'
             ),
             (
                 95,
                 'Excelente dominio de los contenidos y autonomía en la resolución.',
                 'carla.estudiante',
                 'MAT-INT-A'
             ),
             (
                 84,
                 'Muy buen desempeño oral; se recomienda continuar practicando fluidez.',
                 'carla.estudiante',
                 'ING-BAS-B'
             ),
             (
                 79,
                 'Resultado satisfactorio. Debe reforzar comprensión de textos extensos.',
                 'carla.estudiante',
                 'TOE-AVA-A'
             )
     ) AS datos(score, feedback, student_username, group_code)
         JOIN users u ON u.name = datos.student_username
         JOIN students s ON s.user_id = u.id
         JOIN sections sec ON sec.group_code = datos.group_code
         JOIN enrollments e
              ON e.student_id = s.id
                  AND e.section_id = sec.id;

INSERT INTO academic_observations (
    date,
    observation,
    enrollment_id
)
SELECT
    datos.date,
    datos.observation,
    e.id
FROM (
         VALUES
             (
                 DATE '2026-03-13',
                 'La estudiante demuestra constancia y buena disposición para participar en actividades orales.',
                 'ana.estudiante',
                 'ING-BAS-A'
             ),
             (
                 DATE '2026-03-14',
                 'Se recomienda reforzar operaciones con fracciones y resolución de problemas de varios pasos.',
                 'ana.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-15',
                 'El estudiante presenta avances en expresión escrita, aunque requiere mayor precisión gramatical.',
                 'bruno.estudiante',
                 'COM-AVA-A'
             ),
             (
                 DATE '2026-03-15',
                 'La estudiante mantiene un rendimiento destacado y apoya a sus compañeros durante el trabajo grupal.',
                 'carla.estudiante',
                 'MAT-INT-A'
             ),
             (
                 DATE '2026-03-16',
                 'Se observa buena comprensión auditiva y adecuada pronunciación en las actividades del curso.',
                 'carla.estudiante',
                 'ING-BAS-B'
             )
     ) AS datos(date, observation, student_username, group_code)
         JOIN users u ON u.name = datos.student_username
         JOIN students s ON s.user_id = u.id
         JOIN sections sec ON sec.group_code = datos.group_code
         JOIN enrollments e
              ON e.student_id = s.id
                  AND e.section_id = sec.id;

INSERT INTO level_evaluations (
    result,
    date,
    student_id,
    course_id
)
SELECT
    datos.result,
    datos.date,
    s.id,
    c.id
FROM (
         VALUES
             (
                 'APPROVED',
                 DATE '2026-02-20',
                 'ana.estudiante',
                 'Inglés Comunicacional'
             ),
             (
                 'REPEAT',
                 DATE '2026-02-21',
                 'bruno.estudiante',
                 'Matemática Aplicada'
             ),
             (
                 'APPROVED',
                 DATE '2026-02-22',
                 'carla.estudiante',
                 'Comunicación Efectiva'
             ),
             (
                 'APPROVED',
                 DATE '2026-02-23',
                 'carla.estudiante',
                 'Preparación TOEFL Junior'
             )
     ) AS datos(result, date, student_username, course_name)
         JOIN users u ON u.name = datos.student_username
         JOIN students s ON s.user_id = u.id
         JOIN courses c ON c.name = datos.course_name;

INSERT INTO notification (message, "read", user_id)
SELECT
    datos.message,
    datos.read,
    u.id
FROM (
         VALUES
             (
                 'Tu matrícula en Inglés Comunicacional - grupo ING-BAS-A fue registrada correctamente.',
                 FALSE,
                 'ana.estudiante'
             ),
             (
                 'Se publicó una nueva evaluación para tu matrícula de Matemática Aplicada.',
                 FALSE,
                 'ana.estudiante'
             ),
             (
                 'Tu asistencia del 12 de marzo fue registrada como justificada.',
                 TRUE,
                 'bruno.estudiante'
             ),
             (
                 'Tienes una nueva observación académica en Comunicación Efectiva.',
                 FALSE,
                 'bruno.estudiante'
             ),
             (
                 'Resultado de evaluación: APPROVED',
                 FALSE,
                 'carla.estudiante'
             ),
             (
                 'Tu resultado académico fue informado a tu apoderada.',
                 TRUE,
                 'carla.estudiante'
             ),
             (
                 'Resultado académico del estudiante: APPROVED',
                 FALSE,
                 'patricia.apoderada'
             ),
             (
                 'La asistencia de Bruno Rojas fue registrada como justificada.',
                 TRUE,
                 'roberto.apoderado'
             ),
             (
                 'Resultado académico del estudiante: APPROVED',
                 FALSE,
                 'sofia.apoderada'
             ),
             (
                 'Se actualizaron las evaluaciones del curso Inglés Comunicacional.',
                 FALSE,
                 'diego.docente'
             ),
             (
                 'La sección MAT-INT-A tiene nuevas matrículas activas.',
                 TRUE,
                 'elena.docente'
             ),
             (
                 'Se registraron nuevos resultados de evaluación de nivel.',
                 FALSE,
                 'fernando.coordinador'
             ),
             (
                 'El curso Preparación TOEFL Junior tiene una evaluación pendiente de revisión.',
                 FALSE,
                 'gabriela.coordinadora'
             )
     ) AS datos(message, read, username)
         JOIN users u ON u.name = datos.username;