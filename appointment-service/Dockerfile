# Lightweight, secure Alpine-based Java 17 JRE (~80-100 MB final image)
FROM eclipse-temurin:17-jre-alpine

# Metadata for better image management
LABEL maintainer="santoshlimbale76" \
      description="Appointment Service - Core microservice for managing hospital appointments" \
      version="0.0.1-SNAPSHOT"

# Set working directory
WORKDIR /app

# Copy the exact JAR file name you mentioned
# (If you prefer flexibility for future versions, you can use wildcard: appointment-service-*.jar)
COPY target/*-exec.jar app.jar

# Expose the default port for appointment-service
# Change 9090 if your application.yml uses a different server.port
EXPOSE 9090

# Fix encoding issues (critical for Windows Jenkins agents)
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Default environment variables - override in docker-compose.yml or Jenkins for different environments
ENV SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" \
    SPRING_DATASOURCE_USERNAME="root" \
    SPRING_DATASOURCE_PASSWORD="root" \
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/" \
    SERVER_PORT="9090" \
    LOGGING_LEVEL_COM_HOSPITAL="INFO"

# Health check using Spring Boot Actuator (requires actuator dependency)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:9090/actuator/health || exit 1

# Security best practice: Run container as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]