# Lightweight Java 17 runtime only – no Maven build inside Docker
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the JAR that was already built by Jenkins (mvn clean package in root)
COPY target/doctor-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your doctor-service uses
EXPOSE 9091

# Database & Eureka connection settings (can be overridden at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/testdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/

# Prevent encoding problems (especially useful on Windows)
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]