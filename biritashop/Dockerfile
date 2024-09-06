#FROM openjdk:8-jdk-alpine
#COPY target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT [ "java", "-jar", "app.jar" ]

# Etapa 1: Construir o projeto com Maven
FROM maven:3.8.5-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem final
FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
