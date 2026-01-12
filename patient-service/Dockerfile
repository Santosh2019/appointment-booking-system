# Use lightweight Alpine-based Java 17 JRE (smaller image size)
FROM eclipse-temurin:17-jre-alpine

# Add metadata (good practice)
LABEL maintainer="santoshlimbale76" \
      description="Patient Service for Hospital Appointment Booking System" \
      version="0.0.1-SNAPSHOT"

# Set working directory
WORKDIR /app

# Copy the executable Spring Boot JAR
COPY target/patient-service-*.jar app.jar

# Expose the port
EXPOSE 9092

# Prevent encoding issues on Windows-based Jenkins agents
ENV JAVA_TOOL_OPTIONS=" -Dfile.encoding=UTF-8"

# Spring Boot / App environment variables (override in docker-compose for production)
ENV SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="root"
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/"
ENV SERVER_PORT="9092"
ENV LOGGING_LEVEL_COM_HOSPITAL="INFO"

# Health check (recommended)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:9092/actuator/health || exit 1

# Run as non-root user (security best practice)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Entry point
ENTRYPOINT ["java", "-jar", "/app/app.jar"]