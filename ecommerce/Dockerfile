FROM node:21 AS builder1

WORKDIR /app

COPY client/*.json .
COPY client/src src

RUN npm install -g @angular/cli

RUN npm ci && ng build

FROM maven:3-eclipse-temurin-21 AS builder2

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .
COPY --from=builder1 /app/dist/client-side/browser/ src/main/resources/static/

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /apps

COPY --from=builder2 /app/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080 SPRING_DATASOURCE_URL=NOT_SET SPRING_DATASOURCE_USERNAME=NOT_SET SPRING_DATASOURCE_PASSWORD=NOT_SET
ENV SPRING_DATA_MONGODB_URI=NOT_SET

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar