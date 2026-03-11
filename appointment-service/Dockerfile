# Lightweight, secure Alpine-based Java 17 JRE

FROM eclipse-temurin:17-jre-alpine

# Image metadata

LABEL maintainer="santoshlimbale76"
description="Appointment Service - Core microservice for managing hospital appointments"
version="0.0.1-SNAPSHOT"

# Working directory inside container

WORKDIR /app

# Copy built JAR from Maven target folder

COPY target/*.jar app.jar

# Application port

EXPOSE 9090

# Fix encoding issues (important for Windows Jenkins)

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Default environment variables (can be overridden in docker-compose or Jenkins)

ENV SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
SPRING_DATASOURCE_USERNAME="root"
SPRING_DATASOURCE_PASSWORD="root"
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/"
SERVER_PORT="9090"
LOGGING_LEVEL_COM_HOSPITAL="INFO"

# Health check using Spring Boot actuator

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3
CMD wget --spider -q http://localhost:9090/actuator/health || exit 1

# Create non-root user (security best practice)

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

USER appuser

# Start Spring Boot application

ENTRYPOINT ["java","-jar","/app/app.jar"]
