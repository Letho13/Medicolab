
FROM maven:3.9.4-amazoncorretto-21 AS builder

COPY target/discovery-1.0.0-SNAPSHOT.jar discovery.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","discovery.jar"]