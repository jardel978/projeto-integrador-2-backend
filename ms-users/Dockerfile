FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/target/ms-users.jar
COPY ${JAR_FILE} ms-users.jar
EXPOSE 8084
ENV KCK_USERS_CLIENT_SECRET=jcTYZuY8rpxpizZmYcd784l18mXBzBxW
ENTRYPOINT ["java","-jar","ms-users.jar"]