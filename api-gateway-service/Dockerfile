# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR from target folder
COPY target/api-gateway-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port configured in application.properties
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java","-jar","/app/app.jar"]
