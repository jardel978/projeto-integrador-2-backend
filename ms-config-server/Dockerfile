FROM openjdk:17
ARG JAR_FILE=/target/ms-config-server.jar
COPY ${JAR_FILE} ms-config-server.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","ms-config-server.jar"]