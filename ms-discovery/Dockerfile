FROM ubuntu:14.04
RUN apt-get update
RUN apt-get install openjdk:17-oracle -y
ARG JAR_FILE=/target/ms-discovery.jar
COPY ${JAR_FILE} ms-discovery.jar
ENTRYPOINT ["java","-jar","ms-discovery.jar"]









