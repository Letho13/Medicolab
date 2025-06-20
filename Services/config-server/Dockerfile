
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
#COPY --from=builder /app/config-server/target/config-server-1.0.0-SNAPSHOT.jar config-server.jar

COPY target/config-server-1.0.0-SNAPSHOT.jar config-server.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","config-server.jar"]