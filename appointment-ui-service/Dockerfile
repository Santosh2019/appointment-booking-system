# Lightweight Alpine-based JRE
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="santoshlimbale76" \
      description="Appointment UI Service (Thymeleaf Frontend)" \
      version="0.0.1-SNAPSHOT"

WORKDIR /app

# Copy executable JAR
COPY target/appointment-ui-service-*.jar app.jar

# Expose UI port (commonly 8080 or 8085 for frontend services)
EXPOSE 8085

# Fix encoding on Windows Jenkins
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Default config - override in docker-compose.yml
ENV SERVER_PORT=8085 \
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://eureka-service:8761/eureka/" \
    LOGGING_LEVEL_COM_HOSPITAL="INFO"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8085/actuator/health || exit 1

# Non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]