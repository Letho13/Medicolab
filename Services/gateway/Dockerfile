
FROM maven:3.9.4-amazoncorretto-21 AS builder

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
#COPY --from=builder /app/gateway/target/gateway-1.0.0-SNAPSHOT.jar gateway.jar

COPY target/gateway-1.0.0-SNAPSHOT.jar gateway.jar

EXPOSE 8222

ENTRYPOINT ["java","-jar","gateway.jar"]