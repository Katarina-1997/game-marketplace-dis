FROM gradle:9.1.0-jdk17-alpine AS build

WORKDIR /app

COPY . .

RUN gradle :catalog-service:bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/catalog-service/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]