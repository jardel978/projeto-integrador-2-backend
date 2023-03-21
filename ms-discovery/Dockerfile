FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/target/ms-discovery.jar
COPY ${JAR_FILE} ms-discovery.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","ms-discovery.jar"]