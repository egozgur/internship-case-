FROM openjdk:17-ea-17-slim
LABEL maintainer="egemenozgur"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=./target/suggestai-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]