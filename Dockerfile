FROM maven:3.9.6-eclipse-temurin-17
EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-DAPP_PORT=8080", "-jar", "app.jar"]