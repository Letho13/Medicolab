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
#COPY --from=builder /app/note-service/target/note-service-1.0.0-SNAPSHOT.jar note-service.jar

COPY target/note-service-1.0.0-SNAPSHOT.jar note-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","note-service.jar"]