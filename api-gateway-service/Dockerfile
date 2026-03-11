FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="santoshlimbale76" \
      description="API Gateway Service with JWT Authentication" \
      version="0.0.1-SNAPSHOT"

WORKDIR /app

# Install wget for healthcheck
RUN apk add --no-cache wget

COPY target/*.jar app.jar

EXPOSE 8081

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

ENV SERVER_PORT=8081 \
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/ \
    LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_CLOUD_GATEWAY=DEBUG \
    LOGGING_LEVEL_REACTOR_NETTY=INFO

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]