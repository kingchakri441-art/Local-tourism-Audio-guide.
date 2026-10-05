# Local Tourism Audio Guide — Database + Deployment Edition

A React + Java 17/Spring Boot tourism platform with PostgreSQL by default and MySQL support, designed for local development and Render deployment.

## Included
- React responsive frontend
- Spring Boot REST API
- PostgreSQL production database
- MySQL JDBC driver/support
- JPA/Hibernate persistence
- Flyway database migration
- Persistent tourist sites CRUD API
- AI-style tourist assistant endpoint
- GPS auto-guide, audio, QR guide, planner and rewards UI
- Environment-based configuration (no hard-coded production credentials)
- Dockerfiles for frontend/backend
- Docker Compose for local full-stack development
- Render Blueprint (`render.yaml`)
- Health endpoint: `/actuator/health`

## 1. Fastest local run with PostgreSQL
Requirements: Docker Desktop.

```bash
docker compose up --build
```

Open:
- Frontend: http://localhost:3000
- API: http://localhost:8080/api/sites
- Health: http://localhost:8080/actuator/health

The database is persisted in the `tourism-db` Docker volume.

## 2. Run without Docker
Create PostgreSQL database:
```sql
CREATE DATABASE tourism;
```

Backend:
```bash
cd backend
export DB_URL=jdbc:postgresql://localhost:5432/tourism
export DB_USERNAME=postgres
export DB_PASSWORD=YOUR_PASSWORD
mvn spring-boot:run
```

Frontend:
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

Windows PowerShell uses `$env:DB_URL=...` instead of `export`.

## 3. MySQL option
Use the MySQL JDBC driver and set:
```text
DB_URL=jdbc:mysql://localhost:3306/tourism?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=YOUR_PASSWORD
DB_DRIVER=com.mysql.cj.jdbc.Driver
```
For MySQL, point Flyway to the MySQL migration location by adding:
```text
spring.flyway.locations=classpath:db/migration/mysql
```
The JPA model and REST API stay unchanged.

## 4. REST API
- `GET /api/sites` — all sites
- `GET /api/sites?q=warangal` — search sites
- `GET /api/sites/{id}` — one site
- `POST /api/sites` — create site
- `PUT /api/sites/{id}` — update site
- `DELETE /api/sites/{id}` — delete site
- `POST /api/assistant` — tourism assistant
- `GET /actuator/health` — deployment health check

Example create request:
```json
{
  "name":"Gandikota Fort",
  "state":"Andhra Pradesh",
  "category":"Fort",
  "lat":14.8146,
  "lon":78.2884,
  "description":"Pennar canyon fort",
  "history":"Historic fort complex",
  "food":"Rayalaseema cuisine",
  "hidden":"Pennar viewpoint at sunrise",
  "audio":"https://example.com/audio.mp3"
}
```

## 5. Deploy to Render
1. Push this project to GitHub.
2. In Render, choose **New + → Blueprint** and select the repository.
3. Render reads `render.yaml` and creates the API, frontend and managed PostgreSQL database. The Blueprint uses Render's database connection string and the backend container converts it to the JDBC form Spring Boot expects.
4. After the frontend URL is known, change `CORS_ALLOWED_ORIGINS` in the API service to the real frontend URL and redeploy.
5. Render's free PostgreSQL option is currently time-limited; for longer-lived production use, select an appropriate paid Postgres plan. Render provides the internal connection string for services in the same region.

### Important production notes
- Replace the placeholder frontend URL in `render.yaml` before deployment if needed.
- Do not commit real database passwords or API keys.
- The current assistant is rule-based; connect a real LLM provider through a server-side secret if you want production AI chat.
- Replace demo audio URLs with your own licensed tourism audio.
