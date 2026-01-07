# Use lightweight Java 17 runtime only (no Maven needed)
FROM eclipse-temurin:17-jre
# Set working directory
WORKDIR /app
# Copy the pre-built JAR from the Jenkins Maven build
# The JAR is in the target folder after mvn clean package runs in the root
COPY target/eureka-service-0.0.1-SNAPSHOT.jar app.jar
# Expose the Eureka server port
EXPOSE 8761
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]