
FROM maven:3.9.4-amazoncorretto-21 AS builder

COPY target/security-config-1.0.0-SNAPSHOT.jar security-config.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","security-config.jar"]