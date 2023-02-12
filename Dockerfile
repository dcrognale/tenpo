FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY tenpo/.mvn .mvn
COPY tenpo/mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY tenpo/src ./src

CMD ["./mvnw", "spring-boot:run"]