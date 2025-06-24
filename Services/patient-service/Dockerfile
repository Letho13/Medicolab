
FROM maven:3.9.4-amazoncorretto-21 AS builder

COPY target/patient-service-1.0.0-SNAPSHOT.jar patient-service.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","patient-service.jar"]