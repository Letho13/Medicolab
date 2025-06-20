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
#COPY --from=builder /app/risque-service/target/risque-service-1.0.0-SNAPSHOT.jar risque-service.jar

COPY target/risque-service-1.0.0-SNAPSHOT.jar risque-service.jar


EXPOSE 8082

ENTRYPOINT ["java","-jar","risque-service.jar"]