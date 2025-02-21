FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY build/libs/tabletennis-0.0.1-SNAPSHOT.jar simpleProj.jar

CMD ["java", "-jar", "simpleProj.jar"]