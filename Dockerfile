FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties", "-jar", "app.jar"]