# STAGE 1: Build
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# STAGE 2: Run
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY --from=build /src/target/littleneighbors-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=test"]
