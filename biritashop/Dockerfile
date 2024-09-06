FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-8-jdk -y
COPY . .

RUN apt-get install maven -y
RUN ./mvnw clean install

FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY --from=build /target/biritashop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
