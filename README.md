# HTDS User Onboarding Backend

A Spring Boot-based backend project for a simple user onboarding flow with OTP verification, registration, and login using MongoDB. This project was built as a demo/backend learning application for handling user onboarding data and credentials in a structured way.

## Overview

This application demonstrates a basic onboarding workflow where a user:

1. Requests an OTP
2. Verifies the OTP
3. Registers personal details
4. Saves username and password credentials
5. Logs in using saved credentials

The application is built with a layered Spring Boot architecture and uses MongoDB as the main database.

## Features

- OTP generation and verification flow
- User registration with personal details
- Credential storage with username and password
- Password hashing using SHA-256
- Login validation against stored credentials
- MongoDB integration for persistence
- RabbitMQ and Kafka configuration included for messaging integration
- CORS support for frontend testing on localhost
- Spring Security configured to allow open local testing
- REST API endpoints for onboarding and login

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Data MongoDB
- Spring Security
- Maven
- MongoDB
- Kafka
- RabbitMQ
- Postman for API testing

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/anshika_project/user_onboarding/app/
│   │       ├── config/
│   │       │   └── SecurityConfig.java
│   │       ├── controller/
│   │       │   └── AuthController.java
│   │       ├── dto/
│   │       │   ├── ApiResponse.java
│   │       │   ├── CredentialDto.java
│   │       │   ├── OtpRequest.java
│   │       │   ├── OtpVerificationRequest.java
│   │       │   └── UserDto.java
│   │       ├── model/
│   │       │   ├── Credential.java
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   ├── CredentialRepository.java
│   │       │   └── UserRepository.java
│   │       ├── service/
│   │       │   ├── AppService.java
│   │       │   ├── OtpService.java
│   │       │   ├── UserService.java
│   │       │   └── UserServiceImpl.java
│   │       ├── HashUtil.java
│   │       └── HtdsDummyAppApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/anshika_project/user_onboarding/app/
            └── HtdsDummyAppApplicationTests.java
```

## API Endpoints

Base URL: `http://localhost:8081`

### 1. Generate OTP

- Method: `POST`
- Endpoint: `/api/generate-otp`

Request body:

```json
{
  "mobileNumber": "9876543210"
}
```

Response:

```json
{
  "message": "OTP generated successfully",
  "otp": "123456"
}
```

Note: The OTP is returned in the response for local testing/demo purposes.

### 2. Verify OTP

- Method: `POST`
- Endpoint: `/api/verify-otp`

Request body:

```json
{
  "mobileNumber": "9876543210",
  "otp": "123456"
}
```

### 3. Register User

- Method: `POST`
- Endpoint: `/api/register-user`

Request body:

```json
{
  "name": "Anshika",
  "age": 25,
  "gender": "female",
  "city": "Delhi",
  "mobileNumber": "9876543210",
  "otp": "123456"
}
```

### 4. Save Credentials

- Method: `POST`
- Endpoint: `/api/save-credentials`

Request body:

```json
{
  "username": "anshika123",
  "password": "myPassword123"
}
```

### 5. Login

- Method: `POST`
- Endpoint: `/api/login`

Request body:

```json
{
  "username": "anshika123",
  "password": "myPassword123"
}
```

### 6. Verify and Save

- Method: `POST`
- Endpoint: `/api/verify-and-save`

This endpoint allows saving user details in one combined flow.

## Authentication and Security

The project includes OTP-based validation and credential-based login.

- User passwords are hashed using SHA-256 before saving.
- Credentials are validated by comparing the submitted password hash with the stored hash.
- Security is intentionally permissive for local development/testing so requests can be tested easily.

In `SecurityConfig`, the application disables CSRF and allows all requests for demo purposes.

```java
http
    .csrf(csrf -> csrf.disable())
    .cors(Customizer.withDefaults())
    .authorizeHttpRequests(auth -> auth
        .anyRequest().permitAll()
    );
```

This is suitable for a demo project but should be restricted in production.

## Database Configuration

The application uses MongoDB.

Configuration in `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/htdsdb
spring.data.mongodb.database=htdsdb
```

Make sure MongoDB is running locally before starting the app.

## Messaging Configuration

The project includes configuration for both Kafka and RabbitMQ:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.kafka.bootstrap-servers=localhost:9092
```

These services are part of the application setup and are included as part of the backend demo environment.

## Setup and Run

### Prerequisites

- Java 17+
- Maven
- MongoDB
- RabbitMQ
- Kafka

### Clone the repository

```bash
git clone https://github.com/theanshikasharma/USER-ONBOARDING.git
cd USER-ONBOARDING
```

### Build project

```bash
mvn clean install
```

### Run application

```bash
mvn spring-boot:run
```

The backend runs on:

```text
http://localhost:8081
```

## Example Flow

```text
User -> Generate OTP -> Verify OTP -> Register User -> Save Credentials -> Login
```

## Notes

- This is a backend/demo project focused on onboarding logic and credential validation.
- The OTP is shown in the API response only for development/testing.
- For production-level security, it is recommended to use stronger password hashing such as BCrypt or Argon2 and proper token-based authentication.
- The current project is focused on learning and demo use rather than production-ready identity management.

## Future Improvements

- Add JWT authentication
- Use BCrypt/Argon2 for password hashing
- Add validation and global exception handling
- Improve OTP expiry logic and storage
- Add role-based access control
- Add Swagger/OpenAPI documentation
- Add unit and integration tests
- Dockerize the application

## Project Status

This is a demo/backend onboarding application built for learning and practical implementation of:

- REST API design
- Spring Boot architecture
- MongoDB integration
- OTP-based verification flows
- Credential validation and security basics

## Summary

> Developed a Spring Boot-based user onboarding and authentication backend using Java, MongoDB, Kafka, and RabbitMQ. Implemented OTP-based registration flow, credential storage using SHA-256 hashing, and login validation with a layered architecture. The project includes REST APIs for onboarding, verification, and authentication, and was designed for local demo/testing purposes.
