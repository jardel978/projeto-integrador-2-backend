FROM openjdk:17
ARG JAR_FILE=/target/ms-accounts.jar
COPY ${JAR_FILE} ms-accounts.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","ms-accounts.jar"]