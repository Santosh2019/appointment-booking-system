FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="santoshlimbale76" \
      description="Appointment UI Service"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8085

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

ENV SERVER_PORT=8085
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://eureka-service:8761/eureka/"
ENV LOGGING_LEVEL_COM_HOSPITAL="INFO"

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]