# Flujo de demostración — Centro de Idiomas

Guía para mostrar cómo el producto resuelve el caso del centro de idiomas.
Usar con la app corriendo en local (backend `http://localhost:8080`, frontend `http://localhost:4200`).

Credenciales de prueba (contraseña para todos: `comzhichi123`):
- Coordinador: `ana.coord@comzhichi.com`
- Profesor: `carlos.prof@comzhichi.com`
- Estudiante: `luis.est@comzhichi.com`
- Padre: `roberto.padre@comzhichi.com`

---

## 1) Problema del caso → cómo lo resuelve el producto

| Necesidad del caso | Funcionalidad del producto |
|---|---|
| Matricularse por idioma y nivel | Estudiante consulta horarios disponibles y se matricula (`/student/matriculas`, `/student/horarios`) |
| Ver historial académico | `/student/progreso` (avance por idioma/curso) |
| Rendir evaluación para avanzar/repetir | Evaluación de nivel en `/teacher/section/:id` → Fin de nivel (APPROVED/FAILED) |
| Profesor registra asistencia | `/teacher/section/:id` → pestaña Asistencia |
| Profesor registra calificaciones | `/teacher/section/:id` → pestaña Evaluaciones |
| Profesor anota observaciones | `/teacher/section/:id` → pestaña Observaciones |
| Coordinador registra cursos/idiomas/niveles | `/coordinator/courses` |
| Asignar profesor por especialidad/idioma | `/coordinator/teachers` y `/coordinator/sections` |
| Organizar horarios sin cruces | Validación de cruce de horarios al matricular/crear sección |
| Reportes de demanda (idiomas/niveles/horarios) | `/coordinator/demanda` |
| Dar de baja a un estudiante | Botón "Dar de baja" en `/coordinator/sections` |
| Historial completo por estudiante entre ciclos | `/student/progreso` y matrículas por estudiante |
| Distinguir avance por idioma (no mezclar) | Progreso filtrado por curso/idioma; prerrequisitos por idioma |

---

## 2) Flujo de demostración sugerido (10–15 min)

### A. Vista del coordinador (administra el centro)
1. Login con `ana.coord@comzhichi.com`.
2. **Cursos** (`/coordinator/courses`): muestra idiomas (Inglés, Francés, Portugués) y niveles (A1, A2, B1) con prerrequisitos.
3. **Secciones** (`/coordinator/sections`): grupos con horarios, aula, vacantes. Click en 👥 para ver estudiantes activos (los dados de baja aparecen en rojo).
4. **Profesores** (`/coordinator/teachers`): profesores con especialidad/idioma.
5. **Horarios** (`/coordinator/horarios`): organización de grupos por día/hora.
6. **Demanda** (`/coordinator/demanda`): reporte de idiomas/niveles/horarios más demandados (vacantes ocupadas vs disponibles).
7. **Dar de baja**: en una sección, seleccionar un estudiante activo → "Dar de baja" → la vacante se devuelve y el estudiante aparece en rojo.

### B. Vista del profesor (seguimiento académico)
1. Logout y login con `carlos.prof@comzhichi.com`.
2. **Home**: cursos/grupos asignados.
3. Entrar a una sección (`/teacher/section/:id`):
   - **Asistencia**: registrar presente/ausente/tardanza/justificado por fecha.
   - **Evaluaciones**: registrar notas (parcial/final) con retroalimentación.
   - **Observaciones**: anotaciones de desempeño por estudiante.
   - **Fin de nivel**: registrar APPROVED/FAILED → decide si avanza o repite.
4. **Evaluaciones** (`/teacher/evaluaciones`): consolidado de calificaciones por sección.
5. **Progreso** (`/teacher/progreso`): seguimiento de estudiantes activos y aprobados.

### C. Vista del estudiante (aprendizaje y matrícula)
1. Logout y login con `luis.est@comzhichi.com`.
2. **Home**: resumen.
3. **Horarios** (`/student/horarios`): consulta horarios disponibles antes de inscribirse.
4. **Matrículas** (`/student/matriculas`): matricularse en un curso/sección (valida prerrequisitos y cruce de horarios).
5. **Prerrequisitos** (`/student/prerequisites`): muestra qué le falta para avanzar (ej. aprobar Inglés A1 para entrar a A2).
6. **Progreso** (`/student/progreso`): historial académico por idioma (Inglés A1 aprobado, Inglés A2 activo), sin mezclar idiomas.
7. **Notificaciones** (`/student/notificaciones`): avisos del centro.

### D. Cierre (valor para el centro)
- El coordinador puede planificar el siguiente ciclo con datos reales de demanda.
- Cada estudiante tiene su progreso por idioma separado (ej. Inglés y Francés en paralelo, sin mezclar historial).
- Las evaluaciones de nivel deciden avance/repetición, registradas por el profesor.
- Bajas y transferencias entre secciones se gestionan sin perder historial.

---

## 3) Puntos clave para destacar ante el profesor
1. **Multi-idioma y multi-nivel**: un mismo estudiante puede llevar Inglés A2 y Francés A1 en paralelo; el progreso se distingue por curso/idioma.
2. **Prerrequisitos encadenados**: A1 → A2 → B1 por idioma, validados al matricularse.
3. **Sin cruces de horario**: el sistema valida que el nuevo horario no se solape con matrículas activas.
4. **Evaluación de nivel**: APPROVED/FAILED decide avance; si reprueba, repite el nivel.
5. **Demanda real**: reporte de idiomas/niveles/horarios más demandados para planificar ciclos.
6. **Historial persistente**: asistencias, notas y observaciones quedan guardadas por estudiante/sección.