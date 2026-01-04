# Use lightweight Java 17 runtime only (no Maven needed)
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the pre-built JAR from the Jenkins Maven build
# The JAR is created in the target folder by the root mvn clean package
COPY target/patient-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the patient-service port
EXPOSE 9092

# Environment variables (optional, can be overridden at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/

# Prevent encoding issues on Windows agents
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]