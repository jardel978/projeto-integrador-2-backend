FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/target/ms-gateway.jar
COPY ${JAR_FILE} ms-gateway.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","ms-gateway.jar"]