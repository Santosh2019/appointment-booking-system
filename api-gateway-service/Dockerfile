# Use lightweight JRE (smaller image, sufficient for running JAR)
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the pre-built JAR from Maven build
COPY target/api-gateway-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the API Gateway port
EXPOSE 8081

# Prevent encoding issues on Windows Jenkins agents
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]