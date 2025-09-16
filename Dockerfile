FROM eclipse-temurin:21
WORKDIR /app
COPY *.jar deviceEntity-app.jar
EXPOSE 1214
ENTRYPOINT ["java", "-jar", "deviceEntity-app.jar"]