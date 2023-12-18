FROM openjdk:17

COPY data/aircraftData.json /data/aircraftData.json

VOLUME /tmp

EXPOSE 8082

ARG JAR_FILE=target/aircraft-info-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} aircraft-info-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "aircraft-info-service.jar"]