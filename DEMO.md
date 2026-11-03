# Flujo detallado de demostración — Centro de Idiomas

Guía paso a paso para mostrar el producto en funcionamiento y cómo resuelve el caso del centro de idiomas.
Duración estimada: 20–30 minutos.

---

## 0) Preparación antes de la demo

1. **Backend (IntelliJ)**
   - Abrir el proyecto `comzhichi-backend` en IntelliJ.
   - Ejecutar la clase `ComzhichiBackendApplication` (botón ▶).
   - Esperar el mensaje `Started ComzhichiBackendApplication`.
   - Verificar en consola que no haya errores y que `data.sql` se haya ejecutado (verás inserts/updates).

2. **Frontend**
   - En una terminal: `cd comzhichi-frontend` → `ng serve -o`.
   - Se abre automáticamente `http://localhost:4200`.

3. **Base de datos (opcional, para mostrar datos crudos)**
   - Abrir pgAdmin 4 → `db_comzhichi` → Tables para mostrar `students`, `enrollments`, `sections`.

4. **Verificar datos iniciales**
   - En pgAdmin o psql, confirmar que existen:
     - 7 secciones (ING-A1-A, ING-A1-B, ING-A2-A, ING-B1-A, FRN-A1-A, FRN-A2-A, POR-A1-A)
     - ING-A1-A con 10 estudiantes activos, ING-A1-B con 7, FRN-A1-A con 7, POR-A1-A con 7.

---

## 1) Credenciales de demostración

Contraseña para todos: `comzhichi123`

| Rol | Email | Uso en la demo |
|---|---|---|
| Coordinador | `ana.coord@comzhichi.com` | Administra cursos, secciones, profesores, demanda |
| Profesor | `carlos.prof@comzhichi.com` | Registra asistencia, notas, observaciones, fin de nivel |
| Estudiante | `luis.est@comzhichi.com` | Se matricula, consulta horarios, ve progreso |
| Estudiante | `andres.est@comzhichi.com` | Ejemplo de estudiante activo en ING-A1-A |
| Padre | `roberto.padre@comzhichi.com` | Ve el progreso de su hijo |

---

## 2) PARTE 1 — Coordinador (administra el centro)

**Login:** `ana.coord@comzhichi.com` / `comzhichi123` → entra al panel de coordinador.

### 2.1 Home del coordinador
- **Qué mostrar:** resumen del centro, accesos rápidos.
- **Qué decir:** "El coordinador tiene una vista centralizada de todo el centro."

### 2.2 Cursos (`/coordinator/courses`)
- **Navegación:** menú → "Cursos".
- **Qué mostrar:** lista de cursos por idioma (Inglés, Francés, Portugués) y nivel (A1, A2, B1).
- **Qué decir:**
  - "Cada curso tiene idioma, nivel y prerrequisito (ej. Inglés A2 requiere A1)."
  - "El coordinador registra nuevos cursos/idiomas/niveles conforme el centro los incorpora."
- **Acción demo:** crear un curso nuevo (ej. "Inglés B2") o editar uno existente para mostrar el CRUD.

### 2.3 Profesores (`/coordinator/teachers`)
- **Navegación:** menú → "Profesores".
- **Qué mostrar:** profesores con especialidad (Inglés, Francés, Portugués) y estado activo.
- **Qué decir:** "La asignación de profesor se hace según idioma que domina y horario disponible, evitando cruces."

### 2.4 Secciones (`/coordinator/sections`)
- **Navegación:** menú → "Secciones".
- **Qué mostrar:** tarjetas de secciones con código (ING-A1-A), curso, días, horario, aula, vacantes (ej. `9 / 20`).
- **Acción demo:**
  1. Click en el ícono 👥 de `ING-A1-A`.
  2. Se abre el panel de estudiantes: muestra los 10 estudiantes activos.
  3. Explicar: "Solo se muestran los activos; los dados de baja aparecen en rojo tachado con '(Dado de baja)'."
- **Dar de baja (demo):**
  1. En el panel de `FRN-A1-A`, seleccionar "Valeria Estudiante" (activa) → click "Dar de baja".
  2. Confirmar → la vacante se devuelve (`available_vacancies` +1) y Valeria pasa a rojo tachado.
  3. **Qué decir:** "El estudiante que abandona queda registrado como DROPPED (historial preservado) y la vacante se libera."
- **Transferir (demo):**
  1. Click "Transferir" en un estudiante activo.
  2. Seleccionar sección destino del mismo curso con vacantes.
  3. Confirmar → el estudiante pasa a la nueva sección.
- **Editar/eliminar sección:** mostrar botones ✏️ y 🗑️.

### 2.5 Horarios (`/coordinator/horarios`)
- **Navegación:** menú → "Horarios".
- **Qué mostrar:** grupos organizados por día/hora.
- **Qué decir:** "Evita cruces: al matricular o crear sección, el sistema valida solapamiento de horarios."

### 2.6 Demanda (`/coordinator/demanda`)
- **Navegación:** menú → "Demanda".
- **Qué mostrar:** reporte de idiomas/niveles/horarios con mayor demanda (vacantes ocupadas vs disponibles).
- **Qué decir:**
  - "El centro puede planificar mejor los próximos ciclos con datos reales."
  - Mostrar que ING-A1-A tiene 10/20 ocupadas, etc.

---

## 3) PARTE 2 — Profesor (seguimiento académico)

**Logout** (esquina superior) → **Login:** `carlos.prof@comzhichi.com` / `comzhichi123`.

### 3.1 Home del profesor
- **Qué mostrar:** cursos y grupos asignados al profesor (Inglés: ING-A1-A, ING-A1-B, ING-A2-A).
- **Qué decir:** "El profesor solo ve sus grupos asignados."

### 3.2 Detalle de sección (`/teacher/section/:id`)
- **Navegación:** click en una sección (ej. ING-A1-A) o URL `/teacher/section/1`.
- **Lista de estudiantes:** muestra estudiantes activos (los dados de baja en rojo y no seleccionables).

#### Pestaña Asistencia
1. Seleccionar un estudiante (click en su nombre).
2. Llenar: Fecha, Estado (Presente/Ausente/Tardanza/Justificado), Observación.
3. Click "Registrar asistencia".
4. **Qué mostrar:** tabla de asistencias registradas (fecha, estado, observación).
5. **Qué decir:** "El profesor registra asistencia por clase y por estudiante."

#### Pestaña Evaluaciones
1. Con el estudiante seleccionado, llenar: Tipo (Parcial/Final), Fecha, Nota (0–100), Retroalimentación.
2. Click "Registrar evaluación".
3. **Qué mostrar:** tabla de evaluaciones con notas.
4. **Qué decir:** "Las calificaciones quedan guardadas por estudiante y sección."

#### Pestaña Observaciones
1. Llenar: Fecha, Observación.
2. Click "Registrar observación".
3. **Qué mostrar:** tarjetas con observaciones por fecha.
4. **Qué decir:** "El profesor anota desempeño para dar seguimiento en siguientes sesiones."

#### Pestaña Fin de nivel
1. Si el estudiante no tiene evaluación de nivel: llenar Fecha, Resultado (Aprobado/Reprobado), Comentarios → "Registrar evaluación de nivel".
2. Si ya tiene: muestra el resultado (Aprobado/Reprobado, fecha, comentarios).
3. **Qué decir:** "Esto decide si el estudiante avanza al siguiente nivel o repite. Si aprueba, su inscripción pasa a APPROVED (prerrequisito cumplido)."

### 3.3 Evaluaciones (`/teacher/evaluaciones`)
- **Navegación:** menú → "Evaluaciones".
- **Qué mostrar:** consolidado de todas las calificaciones por sección (estudiante, sección, tipo, fecha, nota, feedback).
- **Qué decir:** "Vista global de calificaciones para el profesor."

### 3.4 Progreso (`/teacher/progreso`)
- **Navegación:** menú → "Progreso".
- **Qué mostrar:** estudiantes activos y aprobados por sección, con sus evaluaciones.
- **Qué decir:** "Seguimiento del desempeño a lo largo del tiempo."

### 3.5 Horario (`/teacher/horario`)
- **Navegación:** menú → "Horario".
- **Qué mostrar:** horario semanal del profesor.

### 3.6 Ajustes (`/teacher/ajustes`)
- **Qué mostrar:** perfil del usuario (nombre, correo, rol, estado) cargado al instante, cambio de contraseña.

---

## 4) PARTE 3 — Estudiante (aprendizaje y matrícula)

**Logout** → **Login:** `luis.est@comzhichi.com` / `comzhichi123`.

### 4.1 Home del estudiante
- **Qué mostrar:** resumen y accesos.

### 4.2 Horarios (`/student/horarios`)
- **Navegación:** menú → "Horarios".
- **Qué mostrar:** horarios disponibles por sección (días, hora, aula, vacantes).
- **Qué decir:** "El estudiante consulta horarios disponibles antes de inscribirse."

### 4.3 Matrículas (`/student/matriculas`)
- **Navegación:** menú → "Matrículas".
- **Qué mostrar:** matrículas activas del estudiante (ej. Luis tiene Inglés A1 aprobado y A2 activo).
- **Acción demo (matricularse):**
  1. Seleccionar un curso/sección disponible (ej. Inglés A1 sección B).
  2. Click matricular.
  3. **Validaciones automáticas:**
     - Prerrequisito: si quiere A2, debe haber aprobado A1.
     - Cruce de horarios: no puede solaparse con otra matrícula activa.
  4. **Qué decir:** "El sistema evita matrículas inválidas automáticamente."

### 4.4 Prerrequisitos (`/student/prerequisites`)
- **Navegación:** menú → "Prerrequisitos".
- **Qué mostrar:** qué le falta para avanzar (ej. "Debes aprobar Inglés A1 para matricularte en A2").
- **Qué decir:** "El estudiante sabe en todo momento qué necesita para avanzar."

### 4.5 Progreso (`/student/progreso`)
- **Navegación:** menú → "Progreso".
- **Qué mostrar:** historial académico por idioma:
  - Inglés A1: APPROVED (2026-03-01)
  - Inglés A2: ACTIVE (2026-08-01)
- **Qué decir:** "El progreso se distingue por idioma: si lleva Inglés y Francés en paralelo, no se mezcla el historial."

### 4.6 Notificaciones (`/student/notificaciones`)
- **Qué mostrar:** avisos del centro.

### 4.7 Ajustes (`/student/ajustes`)
- **Qué mostrar:** perfil cargado al instante + cambio de contraseña.

---

## 5) PARTE 4 — Padre (seguimiento de hijo)

**Logout** → **Login:** `roberto.padre@comzhichi.com` / `comzhichi123`.

- **Qué mostrar:** vista del padre con el progreso de su hijo (Luis).
- **Qué decir:** "El padre puede ver horarios, pagos y avance del estudiante."

---

## 6) Puntos clave para destacar (resumen final)

1. **Multi-idioma y multi-nivel:** un estudiante puede llevar Inglés A2 y Francés A1 en paralelo; el progreso se distingue por curso/idioma.
2. **Prerrequisitos encadenados:** A1 → A2 → B1 por idioma, validados al matricularse.
3. **Sin cruces de horario:** validación automática al matricular o crear secciones.
4. **Evaluación de nivel:** APPROVED/FAILED decide avance o repetición; queda registrado.
5. **Demanda real:** reporte de idiomas/niveles/horarios más demandados para planificar ciclos.
6. **Historial persistente:** asistencias, notas, observaciones y bajas quedan guardadas por estudiante/sección.
7. **Bajas controladas:** al dar de baja, la vacante se libera y el estudiante queda como DROPPED (historial preservado).
8. **Roles diferenciados:** coordinador, profesor, estudiante y padre, cada uno con su vista y permisos.

---

## 7) Datos de prueba destacables

- 8 estudiantes originales + 7 nuevos = 15 estudiantes.
- Secciones con ≥7 estudiantes activos: ING-A1-A (10), ING-A1-B (7), FRN-A1-A (7), POR-A1-A (7).
- Niveles avanzados: ING-A2-A (Luis y Sofía, que aprobaron A1), ING-B1-A (sin estudiantes aún, disponible).
- Valeria tiene inscripción DROPPED + ACTIVE en FRN-A1-A (muestra manejo de bajas y re-matrícula).
- Luis tiene A1 APPROVED + A2 ACTIVE (muestra avance por nivel).