FROM maven:3.9.4-amazoncorretto-21 AS builder


COPY target/risque-service-1.0.0-SNAPSHOT.jar risque-service.jar


EXPOSE 8082

ENTRYPOINT ["java","-jar","risque-service.jar"]