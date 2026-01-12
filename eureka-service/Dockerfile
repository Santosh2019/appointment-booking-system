FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="santoshlimbale76" \
      description="Eureka Server for Hospital Appointment Booking System" \
      version="0.0.1-SNAPSHOT"

WORKDIR /app

# ── THIS IS THE FIX ──
COPY target/*-exec.jar app.jar
# or COPY target/eureka-service-*-exec.jar app.jar
# or COPY target/eureka-service-0.0.1-SNAPSHOT-exec.jar app.jar

EXPOSE 8761

# You can remove this – alpine images default to UTF-8 anyway
# ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

ENV SERVER_PORT=8761 \
    EUREKA_CLIENT_REGISTER_WITH_EUREKA=false \
    EUREKA_CLIENT_FETCH_REGISTRY=false \
    EUREKA_SERVER_ENABLE_SELF_PRESERVATION=false \
    LOGGING_LEVEL_COM_NETFLIX=INFO

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=5 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8761/actuator/health || exit 1

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]