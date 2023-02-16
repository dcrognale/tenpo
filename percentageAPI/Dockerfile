FROM maven:3.8.1 AS build
COPY src ./percentageAPI/src
COPY pom.xml ./percentageAPI
RUN mvn -f ./percentageAPI/pom.xml clean package

FROM openjdk:11
COPY --from=build ./percentageAPI/target/percentageAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]