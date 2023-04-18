FROM openjdk:17
ARG JAR_FILE=/target/ms-users.jar
COPY ${JAR_FILE} ms-users.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","ms-users.jar"]