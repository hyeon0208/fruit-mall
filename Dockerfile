FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/application-oauth.properties /app/application-oauth.properties
COPY src/main/resources/application-real-db.properties /app/application-real-db.properties

ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties,/app/application-oauth.properties,/app/application-real-db.properties", "-Dspring.profiles.active=oper", "-jar", "app.jar"]
