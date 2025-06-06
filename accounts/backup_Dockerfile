FROM openjdk:24-jdk-bullseye

LABEL version="0.0.1"

ARG JAR_FILE=accounts-0.0.1-SNAPSHOT.jar

COPY target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]
