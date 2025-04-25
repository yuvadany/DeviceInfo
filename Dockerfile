FROM openjdk:21-jdk-slim AS builder
WORKDIR /deviceapp
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests
FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=builder /device/target/*.jar deviceApp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "deviceApp.jar"]
