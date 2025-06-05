FROM maven:3.9.4-amazoncorretto-21 AS builder

WORKDIR /app

COPY ../../pom.xml .
COPY ../../Services ./Services

RUN mvn -DskipTests clean package


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/discovery-*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]