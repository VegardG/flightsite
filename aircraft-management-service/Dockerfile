FROM openjdk:17

VOLUME /tmp

EXPOSE 8083

ARG JAR_FILE=target/aircraft-management-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} aircraft-management-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/aircraft-management-service.jar"]