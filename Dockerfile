FROM ubuntu:latest
LABEL authors="r250516"

ENTRYPOINT ["top", "-b"]
# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/HTDS-dummy-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port for the validation
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
# Use the same Java version as in your pom.xml
FROM openjdk:24-slim

# Set working directory
WORKDIR /app

# Copy built JAR into container
COPY target/HTDS-dummy-app-0.0.1-SNAPSHOT.jar app.jar

# Expose application port (change if needed)
EXPOSE 8081

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
