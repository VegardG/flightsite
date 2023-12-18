FROM openjdk:17

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=target/api-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} api-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "api-service.jar"]
