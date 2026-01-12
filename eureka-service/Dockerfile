# Lightweight Alpine-based Java 17 JRE
FROM eclipse-temurin:17-jre-alpine

# Metadata
LABEL maintainer="santoshlimbale76" \
      description="Eureka Server for Hospital Appointment Booking System" \
      version="0.0.1-SNAPSHOT"

# Working directory
WORKDIR /app

# Copy the executable JAR (wild-card for flexibility)
COPY target/eureka-service-*.jar app.jar

# Expose Eureka default port
EXPOSE 8761

# Encoding fix for Windows Jenkins agents
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Eureka-specific defaults (override in docker-compose.yml for production)
ENV SERVER_PORT=8761 \
    EUREKA_CLIENT_REGISTER_WITH_EUREKA=false \
    EUREKA_CLIENT_FETCH_REGISTRY=false \
    EUREKA_SERVER_ENABLE_SELF_PRESERVATION=false \
    LOGGING_LEVEL_COM_NETFLIX=INFO

# Health check (Eureka has a built-in health endpoint)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8761/actuator/health || exit 1

# Run as non-root user (security best practice)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]