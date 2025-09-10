# ==========================
# Stage 1: Build
# ==========================
FROM maven:3.9.3-temurin-21 AS build

WORKDIR /app

# Copiar pom y código fuente
COPY pom.xml .
COPY src ./src

# Build del JAR sin tests (los tests se ejecutan en CI)
RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Run
# ==========================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copiar JAR compilado del stage de build
COPY --from=build /app/target/littleneighbors-0.0.1-SNAPSHOT.jar app.jar

# Crear volumen para logs
VOLUME /app/logs

# Exponer puerto de la app
EXPOSE 8081

# Variables de entorno por defecto (pueden sobrescribirse al correr el contenedor)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/littleneighbors
ENV SPRING_DATASOURCE_USERNAME=user
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
