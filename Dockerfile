# ---- Stage 1: Build ----
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /src

COPY pom.xml .

RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -q -DskipTests package

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /src/target/*.jar app.jar

# Define un argumento para el perfil de Spring
ARG SPRING_PROFILES_ACTIVE=prod

# Pasa el argumento como una variable de entorno
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

EXPOSE 8081

# Ejecuta la aplicación con el perfil de Spring
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]


