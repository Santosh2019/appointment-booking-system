# Lightweight and secure Alpine-based JRE
FROM eclipse-temurin:17-jre-alpine

# Metadata
LABEL maintainer="santoshlimbale76" \
      description="Doctor Service for Hospital Appointment Booking System" \
      version="0.0.1-SNAPSHOT"

# Working directory
WORKDIR /app

# Copy executable JAR (wildcard for version flexibility)
COPY target/*-exec.jar app.jar

# Expose port
EXPOSE 9091

# Fix encoding issues on Windows Jenkins
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Default config - override in docker-compose.yml for production
ENV SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="root"
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/"
ENV SERVER_PORT="9091"
ENV LOGGING_LEVEL_COM_HOSPITAL="INFO"

# Health check using Actuator
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:9091/actuator/health || exit 1

# Security: Run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Start app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]