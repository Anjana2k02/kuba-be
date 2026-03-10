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

Edit `src/main/resources/application.yml` to customize:
- Database connection
- JWT secret (change for production!)
- JWT expiration time
- Server port
- CORS origins

## Production Notes

⚠️ **Security**: Change the JWT secret in `application.yml` before deploying to production:
```yaml
jwt:
  secret: your-secure-secret-key-here
```

## License

This project is open source and available under the MIT License.
