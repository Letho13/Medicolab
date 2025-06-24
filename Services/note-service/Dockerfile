FROM maven:3.9.4-amazoncorretto-21 AS builder

COPY target/note-service-1.0.0-SNAPSHOT.jar note-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","note-service.jar"]