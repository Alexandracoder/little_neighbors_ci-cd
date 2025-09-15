# STAGE 1: Build
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# STAGE 2: Run
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia el JAR construido
COPY --from=build /src/target/littleneighbors-0.0.1-SNAPSHOT.jar app.jar

# Copia el properties de test desde src/test/resources
COPY --from=build /src/src/test/resources/application-test.properties application-test.properties

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=/app/application-test.properties"]

