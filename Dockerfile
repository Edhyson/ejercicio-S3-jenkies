# Imagen base con JDK
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado por Maven/Gradle
COPY target/project_aws-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de tu API
EXPOSE 8081

# Comando de arranque
ENTRYPOINT ["java","-jar","app.jar"]
