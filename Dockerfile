FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties,/app/application-oauth.properties,/app/application-oper.properties", "-jar", "app.jar"]
