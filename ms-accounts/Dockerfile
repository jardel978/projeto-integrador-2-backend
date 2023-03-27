FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/target/ms-accounts.jar
COPY ${JAR_FILE} ms-accounts.jar
EXPOSE 8085
ENV KCK_ACCOUNTS_CLIENT_SECRET=1wsQo4YdD3YBxqKbMjJXq7H3Lh8E5GT1
ENTRYPOINT ["java","-jar","ms-accounts.jar"]