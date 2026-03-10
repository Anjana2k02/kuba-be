# Kuba User API - Backend

A modern Spring Boot REST API for user management with JWT authentication, PostgreSQL database, and Swagger documentation.

## Features

- ✅ User CRUD operations (Create, Read, Update, Delete)
- ✅ User authentication with JWT tokens
- ✅ Password encryption with BCrypt
- ✅ PostgreSQL database integration
- ✅ Swagger/OpenAPI documentation
- ✅ CORS support for frontend integration
- ✅ Spring Security with JWT filter
- ✅ Exception handling and error responses

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 12+

## Database Setup

1. Install PostgreSQL and ensure it's running on `localhost:5432`
2. Create a database:

```sql
CREATE DATABASE kuba_db;
```

3. Update credentials in `src/main/resources/application.yml` if different from default:
   - Default username: `postgres`
   - Default password: `postgres`

## Installation & Setup

1. Navigate to the backend folder:
```bash
cd kuba-be
```

2. Install dependencies and build:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## CI/CD with Docker and Kubernetes (Step by Step)

This project now includes:
- Docker image build for `kuba-be`
- Kubernetes manifests for deployment
- GitHub Actions pipeline for build, push, and deploy

### 1. Understand the delivery flow

1. Push code to `main`
2. GitHub Actions runs Maven tests/build
3. Docker image is built and pushed to GHCR
4. Workflow deploys the new image to your Kubernetes cluster

### 2. Files added for the pipeline

- `.github/workflows/kuba-be-cicd.yml`
- `Dockerfile`
- `.dockerignore`
- `k8s/namespace.yaml`
- `k8s/configmap.yaml`
- `k8s/deployment.yaml`
- `k8s/service.yaml`
- `k8s/secret.example.yaml`

### 3. Configure GitHub repository secrets

In your GitHub repository, open **Settings > Secrets and variables > Actions** and add:

- `KUBE_CONFIG_DATA`: base64-encoded kubeconfig content
- `DB_USERNAME`: database username for production cluster
- `DB_PASSWORD`: database password for production cluster
- `JWT_SECRET`: secure JWT secret for production

Optional variable:
- `K8S_NAMESPACE`: namespace name (default is `kuba`)

How to get `KUBE_CONFIG_DATA`:

```bash
cat ~/.kube/config | base64
```

### 4. Prepare Kubernetes dependencies

The backend deployment expects PostgreSQL reachable at:

`jdbc:postgresql://postgres:5432/kuba_db`

If your DB host is different, change `DB_URL` in `k8s/configmap.yaml`.

### 5. First deployment (manual bootstrap)

Before first CI run, apply base manifests once:

```bash
kubectl apply -f kuba-be/k8s/namespace.yaml
kubectl apply -f kuba-be/k8s/configmap.yaml
kubectl apply -f kuba-be/k8s/service.yaml
kubectl apply -f kuba-be/k8s/deployment.yaml
```

You can also create secret manually for local cluster checks:

```bash
kubectl -n kuba create secret generic kuba-be-secret \
  --from-literal=DB_USERNAME=postgres \
  --from-literal=DB_PASSWORD=postgres \
  --from-literal=JWT_SECRET=change-me
```

The CI workflow will upsert this secret on each deployment using GitHub secrets.

### 6. Trigger CI/CD

1. Commit and push to `main`
2. Open **Actions** tab in GitHub
3. Watch workflow `Kuba BE CI/CD`
4. Validate rollout:

```bash
kubectl -n kuba get pods
kubectl -n kuba rollout status deployment/kuba-be
```

### 7. Local Docker test (recommended)

From `kuba-be` folder:

```bash
docker build -t kuba-be:local .
docker run --rm -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/kuba_db \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  -e JWT_SECRET=local-dev-secret \
  kuba-be:local
```

## API Endpoints

### Authentication
- `POST /api/users/login` - Login with email and password
- `POST /api/users/register` - Register a new user

### Users (Protected - Requires JWT Token)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user name and/or password
- `DELETE /api/users/{id}` - Delete user

## Swagger Documentation

Visit `http://localhost:8080/api/swagger-ui.html` to access the interactive API documentation.

## Request Examples

### Register
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "name": "John Doe",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Get All Users (with token)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <your-jwt-token>"
```

### Update User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer <your-jwt-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe",
    "password": "newpassword123"
  }'
```

## Project Structure

```
kuba-be/
├── src/main/java/com/kuba/
│   ├── KubaApplication.java         # Main Spring Boot application
│   ├── controller/                  # REST controllers
│   ├── service/                     # Business logic
│   ├── repository/                  # Data access layer
│   ├── entity/                      # JPA entities
│   ├── dto/                         # Data Transfer Objects
│   ├── security/                    # JWT and security config
│   ├── config/                      # Application configuration
│   └── exception/                   # Error handling
├── src/main/resources/
│   └── application.yml              # Application configuration
└── pom.xml                          # Maven configuration
```

## Technologies

- **Spring Boot 3.2.0** - Web framework
- **Spring Data JPA** - Database access
- **Spring Security** - Authentication & authorization
- **JWT (jjwt)** - Token-based authentication
- **PostgreSQL** - Database
- **Swagger/SpringDoc** - API documentation
- **Lombok** - Boilerplate reduction
- **Maven** - Build tool

## Configuration

The app now supports environment variables (with local defaults) in `src/main/resources/application.yml`:
- Database connection
- JWT secret (change for production!)
- JWT expiration time
- Server port
- CORS origins

Environment variables used by deployment:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `SERVER_PORT`
- `SERVER_CONTEXT_PATH`

## Production Notes

⚠️ **Security**:
- Never commit real secrets to git.
- Set a strong `JWT_SECRET` in GitHub Secrets.
- Use a managed PostgreSQL instance or a secured DB deployment.

## License

This project is open source and available under the MIT License.
