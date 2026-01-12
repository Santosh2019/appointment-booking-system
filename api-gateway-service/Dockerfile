# Lightweight and secure Alpine-based JRE
FROM eclipse-temurin:17-jre-alpine

# Metadata
LABEL maintainer="santoshlimbale76" \
      description="API Gateway Service with JWT Authentication" \
      version="0.0.1-SNAPSHOT"

# Working directory
WORKDIR /app

# Copy executable JAR (wildcard for flexibility)
COPY target/*-exec.jar app.jar

# Expose Gateway port
EXPOSE 8081

# Fix encoding issues on Windows Jenkins

# Default config - override in docker-compose.yml
ENV SERVER_PORT=8081 \
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/" \
    LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_CLOUD_GATEWAY="DEBUG" \
    LOGGING_LEVEL_REACTOR_NETTY="INFO"

# Health check via Actuator (Gateway exposes /actuator/gateway/routes too)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Run as non-root user (security best practice)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Start the Gateway
ENTRYPOINT ["java", "-jar", "/app/app.jar"]