FROM maven:3.8.1 AS build
COPY src ./tenpoAPI/src
COPY pom.xml ./tenpoAPI
RUN mvn -f ./tenpoAPI/pom.xml clean package

FROM openjdk:11
COPY --from=build ./tenpoAPI/target/tenpo-0.0.1-SNAPSHOT.jar app1.jar
ENTRYPOINT ["java","-jar","/app1.jar"]