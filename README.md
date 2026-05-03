# ejercicio-S3-jenkies
This a test app with S3 and jenkies





Guía: Levantar API con Docker + LocalStack

Este documento explica cómo levantar una API en Docker, configurar LocalStack y trabajar con buckets S3 simulados en un ambiente local.

------------------------------------------------------------
1. Crear imagen de la API
------------------------------------------------------------
- Compilar el proyecto con Maven:
  mvn clean package

- Crear un Dockerfile:
  FROM eclipse-temurin:17-jdk-alpine
  WORKDIR /app
  COPY target/app.jar app.jar
  EXPOSE 8081
  ENTRYPOINT ["java","-jar","app.jar"]

- Construir la imagen:
  docker build -t mi-api-s3:1.0 .

------------------------------------------------------------
2. Configurar Docker en Ubuntu
------------------------------------------------------------
- Instalar Docker y Docker Compose:
  sudo apt update
  sudo apt install docker.io docker-compose -y

- Verificar instalación:
  docker --version
  docker-compose --version

------------------------------------------------------------
3. Usar la imagen de LocalStack
------------------------------------------------------------
- Imagen oficial: localstack/localstack:3.0.2

------------------------------------------------------------
4. Configurar docker-compose.yml
------------------------------------------------------------
version: "3.8"
services:
  localstack:
    image: localstack/localstack:3.0.2
    container_name: localstack
    ports:
      - "4566:4566"
    environment:
      - SERVICES=s3
    networks:
      - mynet

  api:
    image: mi-api-s3:1.0
    container_name: mi-api
    ports:
      - "8080:8081"
    environment:
      - AWS_ACCESS_KEY_ID=test
      - AWS_SECRET_ACCESS_KEY=test
      - AWS_REGION=us-east-1
      - AWS_ENDPOINT=http://localstack:4566
    depends_on:
      - localstack
    networks:
      - mynet

networks:
  mynet:
    driver: bridge

- Levantar todo:
  docker-compose up -d

------------------------------------------------------------
5. Crear y validar bucket en LocalStack
------------------------------------------------------------
- Crear bucket:
  aws --endpoint-url=http://localhost:4566 s3 mb s3://mi-bucket-prueba

- Listar buckets:
  aws --endpoint-url=http://localhost:4566 s3 ls

- Mostrar contenido de un bucket:
  aws --endpoint-url=http://localhost:4566 s3 ls s3://mi-bucket-prueba

Credenciales usadas:
- AWS_ACCESS_KEY_ID=test
- AWS_SECRET_ACCESS_KEY=test

------------------------------------------------------------
6. Comandos útiles de Docker
------------------------------------------------------------
- Listar imágenes creadas:
  docker images

- Levantar imagen unitaria:
  docker run -p 8080:8081 mi-api-s3:1.0

- Levantar compose:
  docker-compose up -d

- Bajar imagen unitaria:
  docker stop <container_id>
  docker rm <container_id>

- Bajar compose:
  docker-compose down

------------------------------------------------------------
7. Notas adicionales
------------------------------------------------------------
- Puertos: la API corre en 8081 dentro del contenedor, expuesto como 8080 en el host.
- Red interna: con docker-compose, la API habla con LocalStack usando http://localstack:4566.
- Logs: revisar con
  docker logs mi-api

------------------------------------------------------------
📌 Cheat Sheet rápido
------------------------------------------------------------
- Construir imagen API: docker build -t mi-api-s3:1.0 .
- Levantar API sola: docker run -p 8080:8081 mi-api-s3:1.0
- Levantar stack completo: docker-compose up -d
- Parar stack completo: docker-compose down
- Listar imágenes: docker images
- Listar contenedores: docker ps
- Ver logs de API: docker logs mi-api
- Crear bucket: aws --endpoint-url=http://localhost:4566 s3 mb s3://mi-bucket-prueba
- Listar buckets: aws --endpoint-url=http://localhost:4566 s3 ls
- Listar contenido bucket: aws --endpoint-url=http://localhost:4566 s3 ls s3://mi-bucket-prueba
