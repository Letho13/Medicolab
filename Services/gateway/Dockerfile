
FROM maven:3.9.4-amazoncorretto-21 AS builder

COPY target/gateway-1.0.0-SNAPSHOT.jar gateway.jar

EXPOSE 8222

ENTRYPOINT ["java","-jar","gateway.jar"]