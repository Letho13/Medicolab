
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
#COPY --from=builder /app/patient-service/target/patient-service-1.0.0-SNAPSHOT.jar patient-service.jar

COPY target/patient-service-1.0.0-SNAPSHOT.jar patient-service.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","patient-service.jar"]