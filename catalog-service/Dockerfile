FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :catalog-service:bootJar -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/catalog-service/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]