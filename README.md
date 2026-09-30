# MindingMyBookness

MindingMyBookness is a Spring Boot REST API for managing a personal book collection. It provides user registration and login, JWT-based authentication, book searching, and administrator-only book management.

## Tech stack

- Java 21
- Spring Boot 4.0.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JSON Web Tokens (JWT)
- PostgreSQL
- Maven

## Prerequisites

- JDK 21 or later
- PostgreSQL
- A database named `mindingmybookness_database`

## Configuration

The application runs on port `8090` by default and reads the following environment variables:

| Variable | Description |
| --- | --- |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `JWT_SECRET_KEY` | Secret used to sign JWTs |
| `ADMIN_PASSWORD` | Password for the initial administrator account |

Create the PostgreSQL database before starting the application:

```sql
CREATE DATABASE mindingmybookness_database;
```

Set the environment variables in your local environment or in a local `.env` file. The `.env` file is ignored by Git and must not be committed.

## Running the application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8090
```

To run the test suite:

```powershell
.\mvnw.cmd test
```

## Authentication

New users can register with the signup endpoint. The application creates an administrator account named `admin` on startup when no administrator exists, using the value of `ADMIN_PASSWORD`.

Login returns an access token and refresh token. Include the access token in requests that require authentication:

```text
Authorization: Bearer <access-token>
```

## API endpoints

### Users

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| `POST` | `/users/signup` | Create a regular user | Public |
| `POST` | `/users/login` | Authenticate a user | Public |
| `POST` | `/users/refresh` | Generate a new access token | Public |
| `GET` | `/users` | List all users | Authenticated |
| `GET` | `/users/searchUsername/{username}` | Search users by username | Admin |

Signup request:

```json
{
  "username": "reader",
  "password": "password",
  "email": "reader@example.com"
}
```

Login request:

```json
{
  "username": "reader",
  "password": "password"
}
```

Refresh request:

```json
{
  "refreshToken": "<refresh-token>"
}
```

### Books

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| `GET` | `/books` | List all books | Public |
| `GET` | `/books/booknamesearch?name={name}` | Search books by name | Public |
| `GET` | `/books/authorbookssearch?author={author}` | Search books by author | Public |
| `POST` | `/books/addbook` | Add a book | Admin |
| `DELETE` | `/books/deletebook?name={name}` | Delete a book by name | Admin |
| `PUT` | `/books/editbook/{id}` | Edit a book | Admin |
| `PUT` | `/books/editBookMonth/{id}?newMonth={month}` | Change a book's month | Admin |

Book request:

```json
{
  "bookname": "The Hobbit",
  "author": "J. R. R. Tolkien",
  "description": "A fantasy adventure novel.",
  "month": "JAN"
}
```

Valid month values are:

```text
JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC
```

## Project structure

```text
src/
├── main/
│   ├── java/com/mindingmybookness/
│   │   ├── Config/       # Security and application configuration
│   │   ├── Controller/   # REST endpoints
│   │   ├── DTOs/         # Request and response data objects
│   │   ├── Entity/       # JPA entities and enums
│   │   ├── Repository/   # Database repositories
│   │   └── Service/      # Application and JWT services
│   └── resources/
│       └── application.yaml
└── test/
    └── java/             # Application tests
```

## Database behavior

Hibernate is configured with `ddl-auto: update`, so the schema is updated from the entity definitions when the application starts. SQL statements are also enabled for development visibility.

## License

No license has been specified for this project.
