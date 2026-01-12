# PesaPal - Banking Application

A modern, secure banking application built with Spring Boot that provides user management, account operations, and authentication features.

## Overview

PesaPal is a RESTful banking application that enables users to create accounts, perform balance inquiries, credit/debit transactions, and manage their banking information. The application implements JWT-based authentication and includes email notifications for account activities.

## Features

- **User Management**
  - User registration with automatic account number generation
  - User authentication with JWT tokens
  - Role-based access control (Admin, User)

- **Account Operations**
  - Balance inquiry by account number
  - Balance inquiry by email
  - Account credit transactions
  - Account debit transactions (with balance validation)

- **Security**
  - JWT-based authentication
  - Password encryption using BCrypt
  - Spring Security integration
  - Stateless session management

- **Email Service**
  - Automated email notifications
  - Account creation confirmations
  - Account details sharing

- **API Documentation**
  - SpringDoc OpenAPI (Swagger) integration
  - RESTful API endpoints

## Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Documentation**: SpringDoc OpenAPI 2.6.0
- **Email**: Spring Mail (SMTP)
- **Libraries**: 
  - Lombok
  - JJWT (JSON Web Token)
  - SpringFox Swagger UI

## Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+ (or use the included Maven Wrapper)
- PostgreSQL 12+ (or compatible version)
- SMTP email account (for email notifications - Gmail recommended)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/cl4w404/PesaPal---Apply-Now.git
cd PesaPal---Apply-Now
```

### 2. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE bank;
```

### 3. Configuration

Update the `src/main/resources/application.properties` file with your configuration:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/bank
spring.datasource.username=your_username
spring.datasource.password=your_password

# Email Configuration (Gmail example)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password

# JWT Configuration
app.jwt-secret=your_base64_encoded_secret_key
app.jwt-expiration=86400000
```

**Note**: 
- For Gmail, you'll need to generate an App Password (not your regular password)
- The JWT secret should be a Base64 encoded string
- Default JWT expiration is 24 hours (86400000 milliseconds)

### 4. Build the Application

Using Maven Wrapper (recommended):
```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

Or using Maven directly:
```bash
mvn clean install
```

### 5. Run the Application

Using Maven Wrapper:
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Or using Maven directly:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` (default port).

## API Endpoints

### Base URL: `http://localhost:8080/api/v1/user`

| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| POST | `/add` | Create a new user account | No |
| POST | `/login` | User login (returns JWT token) | No |
| GET | `/all` | Get all users | Yes |
| POST | `/balanceEnquiry` | Check balance by account number | Yes |
| POST | `/emailEnquiry` | Check balance by email | Yes |
| POST | `/credit` | Credit funds to an account | Yes |
| POST | `/debit` | Debit funds from an account | Yes |

### API Documentation

Once the application is running, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

Or SpringDoc OpenAPI at:
```
http://localhost:8080/swagger-ui/index.html
```

## Request/Response Examples

### Create User (POST /api/v1/user/add)

**Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "otherName": "Middle",
  "email": "john.doe@example.com",
  "gender": "Male",
  "password": "password123",
  "address": "123 Main St",
  "stateOfOrigin": "Nairobi",
  "dateOfBirth": "1990-01-01",
  "phoneNumber": "+254712345678",
  "alternativePhone": "+254798765432",
  "role": "ROLE_USER"
}
```

**Response:**
```json
{
  "messageCode": "002",
  "message": "User created successfully",
  "data": {
    "accountName": "John Doe Middle",
    "accountNumber": "2024123456",
    "accountBalance": 0.00
  }
}
```

### Login (POST /api/v1/user/login)

**Request:**
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "messageCode": "200",
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### Balance Enquiry (POST /api/v1/user/balanceEnquiry)

**Request:**
```json
{
  "accountNumber": "2024123456"
}
```

**Note**: Include the JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

## Project Structure

```
src/
├── main/
│   ├── java/com/devcorp/bank_proj/
│   │   ├── config/              # Security & JWT configuration
│   │   ├── controller/          # REST controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── models/              # Entity models
│   │   ├── repository/          # Data access layer
│   │   ├── response/            # Response models
│   │   ├── service/             # Business logic
│   │   │   ├── implementations/
│   │   │   └── services/
│   │   └── utils/               # Utility classes
│   └── resources/
│       └── application.properties
└── test/
    └── java/                    # Test files
```

## Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-Based Access**: Admin and User roles
- **CORS Configuration**: Configured for cross-origin requests
- **CSRF Protection**: Disabled for API usage (can be enabled if needed)

## Account Number Generation

Account numbers are automatically generated using the format:
- **Format**: `YYYYNNNNNN`
- **Example**: `2024123456` (Year + 6-digit random number)

## Status Codes

The application uses custom status codes for different scenarios:

| Code | Message |
|------|---------|
| 001 | User already exists |
| 002 | User created successfully |
| 003 | Account does not exist |
| 004 | Account found |
| 005 | Account with email not found |
| 006 | Account with email found |
| 007 | Account debited successfully |
| 008 | Insufficient funds |
| 009 | Account credited successfully |

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available for educational purposes.

## Author

Developed by DevCorp

## Support

For issues and questions, please open an issue on the GitHub repository.

---

**Note**: This application is for educational/demonstration purposes. For production use, consider additional security measures, logging, monitoring, and compliance requirements.
