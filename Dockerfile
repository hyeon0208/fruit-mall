FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-real-db.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-oper.properties", "-Dspring.profiles.active=oper",  "-jar", "app.jar"]
