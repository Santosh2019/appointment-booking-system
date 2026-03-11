# Lightweight and secure Alpine-based JRE

FROM eclipse-temurin:17-jre-alpine

# Metadata

LABEL maintainer="santoshlimbale76"
description="API Gateway Service with JWT Authentication"
version="0.0.1-SNAPSHOT"

# Working directory

WORKDIR /app

# Copy built jar from Maven target folder

COPY target/*.jar app.jar

# Expose Gateway port

EXPOSE 8081

# Fix encoding issues (important for Windows Jenkins)

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Default configuration

ENV SERVER_PORT=8081
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://host.docker.internal:8761/eureka/"
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_CLOUD_GATEWAY="DEBUG"
LOGGING_LEVEL_REACTOR_NETTY="INFO"

# Health check

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3
CMD wget --spider -q http://localhost:8081/actuator/health || exit 1

# Security best practice - run as non root

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

USER appuser

# Start the Gateway

ENTRYPOINT ["java","-jar","/app/app.jar"]
