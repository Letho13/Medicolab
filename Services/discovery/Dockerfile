
FROM maven:3.9.4-amazoncorretto-21 AS builder
#
#WORKDIR /app
#
#COPY pom.xml ./
#
#RUN mvn -DskipTests clean package
#
#FROM eclipse-temurin:21-jre-alpine
#
#WORKDIR /app
#
#COPY --from=builder /app/discovery/target/discovery-1.0.0-SNAPSHOT.jar discovery.jar

COPY target/discovery-1.0.0-SNAPSHOT.jar discovery.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","discovery.jar"]