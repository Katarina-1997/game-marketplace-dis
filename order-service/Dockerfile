FROM gradle:9.1.0-jdk17-alpine AS build

WORKDIR /app

COPY . .

RUN gradle :order-service:bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/order-service/build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]