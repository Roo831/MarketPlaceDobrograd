FROM openjdk:17

COPY target/Marketplace-0.0.13-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]