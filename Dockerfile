FROM eclipse-temurin:21
WORKDIR /app
COPY *.jar device-app.jar
EXPOSE 1214
ENTRYPOINT ["java", "-jar", "device-app.jar"]