FROM openjdk:11-jdk-slim as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:11-jdk-slim
VOLUME /tmp

COPY --from=build /workspace/app/target/converter*.jar /app/app.jar
ENTRYPOINT ["java","-jar","-Duser.country=DE","-Duser.language=de","/app/app.jar"]