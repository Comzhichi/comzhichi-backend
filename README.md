# Comzhichi Backend

Sistema de gestión académica (backend Spring Boot + frontend Angular).

## Requisitos
- Java 21 (JDK)
- Maven 3.9+
- Node.js 20+ y npm
- PostgreSQL 15+ (servicio corriendo)

## Base de datos (una sola vez)
Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE db_comzhichi;
```
Credenciales configuradas en `src/main/resources/application.yaml`:
- Host: `localhost:5432`
- BD: `db_comzhichi`
- Usuario: `postgres`
- Password: `adminadmin`

## Cómo correr en local

### Backend
1. Abrir el proyecto en IntelliJ.
2. Ejecutar la clase principal **`ComzhichiBackendApplication`** desde el botón de Run (▶).
3. El backend queda en `http://localhost:8080`.
   - Al iniciar, Hibernate crea las tablas y `data.sql` carga los datos de prueba automáticamente.

### Frontend
```powershell
cd comzhichi-frontend
npm install
ng serve -o
```
- El frontend queda en `http://localhost:4200` y se abre solo en el navegador.
- Ya apunta al backend (`http://localhost:8080/api`).

## Uso
- Abrir `http://localhost:4200`.
- Login de prueba:
  - Coordinador: `ana.coord@comzhichi.com` / `comzhichi123`
  - Profesor: `carlos.prof@comzhichi.com` / `comzhichi123`
  - Estudiante: `luis.est@comzhichi.com` / `comzhichi123`
- Swagger: `http://localhost:8080/swagger-ui.html`

## Notas
- El backend tarda ~13 s en arrancar (normal en desarrollo).
- Los datos persisten entre reinicios; `data.sql` solo inserta lo que falta.
- Para volver al estado inicial de prueba: borrar y recrear `db_comzhichi` y reiniciar el backend.