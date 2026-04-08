FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="santoshlimbale76" \
      description="Appointment Service - Core microservice for managing hospital appointments" \
      version="0.0.1-SNAPSHOT"

WORKDIR /app

COPY target/app.jar app.jar

EXPOSE 9090

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/testdb \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=root \
    EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-service:8761/eureka/ \
    SERVER_PORT=9090

# install wget for healthcheck
RUN apk add --no-cache wget

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget --spider -q http://localhost:9090/actuator/health || exit 1

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]