-- ============================================================
-- Limpieza de datos de SECCIONES y MODULO DE MATRICULAS
-- Base de datos: PostgreSQL / Supabase (comzhichi-backend)
-- ============================================================
-- Uso:
--   1. Abre el SQL Editor de Supabase.
--   2. Pega este script (o ejecuta el archivo completo).
--   3. Revisa los conteos al final para confirmar.
--
-- IMPORTANTE:
--   - Se eliminan SOLO datos de matriculas, asistencias, evaluaciones,
--     evaluaciones de nivel, observaciones academicas y secciones.
--   - NO se eliminan cursos, estudiantes, docentes ni usuarios.
--     (Los prerequisitos viven en la tabla `courses` y se conservan.)
--   - El orden de borrado respeta las FKs (hijos -> padres).
-- ============================================================

BEGIN;

-- 1) Hijos directos de enrollment
DELETE FROM academic_observations;
DELETE FROM attendances;
DELETE FROM evaluations;
DELETE FROM level_evaluations;

-- 2) Matriculas (dependen de sections)
DELETE FROM enrollments;

-- 3) Secciones
DELETE FROM sections;

-- (Opcional) Si en su lugar quieres CONSERVAR las secciones y solo
-- resetear sus vacantes disponibles tras borrar matriculas, comenta
-- el DELETE FROM sections de arriba y descomenta la siguiente linea:
-- UPDATE sections SET available_vacancies = total_vacancies;

-- 4) (Opcional) Reiniciar secuencias (IDs) de las tablas limpiadas
DO $$
DECLARE
    tbl TEXT;
BEGIN
    FOREACH tbl IN ARRAY ARRAY[
        'academic_observations', 'attendances', 'evaluations',
        'level_evaluations', 'enrollments', 'sections'
    ] LOOP
        IF pg_get_serial_sequence(tbl, 'id') IS NOT NULL THEN
            PERFORM setval(pg_get_serial_sequence(tbl, 'id'), 1, false);
        END IF;
    END LOOP;
END $$;

COMMIT;

-- ============================================================
-- Validacion posterior
-- ============================================================
SELECT 'academic_observations' AS tabla, COUNT(*) AS registros FROM academic_observations
UNION ALL SELECT 'attendances', COUNT(*) FROM attendances
UNION ALL SELECT 'evaluations', COUNT(*) FROM evaluations
UNION ALL SELECT 'level_evaluations', COUNT(*) FROM level_evaluations
UNION ALL SELECT 'enrollments', COUNT(*) FROM enrollments
UNION ALL SELECT 'sections', COUNT(*) FROM sections;

-- Ver prerequisitos configurados en cursos (se conservan)
SELECT c.id, c.name, c.level, p.id AS prereq_id, p.name AS prerequisito
FROM courses c
LEFT JOIN courses p ON p.id = c.prerequisite_id
ORDER BY c.id;
