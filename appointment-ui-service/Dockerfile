# Use lightweight Java 17 runtime only (no Maven needed)
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the pre-built JAR from Jenkins Maven build
COPY target/appointment-ui-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your UI service runs on
EXPOSE 9096

# Optional: Keep useful environment variables (can be overridden at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/testdb
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/

# Prevent encoding issues on Windows agents
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]