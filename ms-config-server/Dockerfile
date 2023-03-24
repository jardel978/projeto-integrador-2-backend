FROM ubuntu:14.04
RUN apt-get update
RUN apt-get install openjdk:17-oracle -y
ARG JAR_FILE=/target/ms-config-server.jar
COPY ${JAR_FILE} ms-config-server.jar
ENTRYPOINT ["java","-jar","ms-config-server.jar"]