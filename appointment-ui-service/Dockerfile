# Stage 1: Build the JAR using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files and source code
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/appointment-ui-service-0.0.1-SNAPSHOT.jar app.jar

# Expose service port
EXPOSE 9096

# Environment variables (optional, can override at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/testdb
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/

# Force UTF-8 encoding (prevents Windows-1252 errors)
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
