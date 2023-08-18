FROM openjdk:11
LABEL maintainer="egemenozgur"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=./target/SpringJwtAuthExample-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]