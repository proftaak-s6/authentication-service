FROM maven:3.6.0-jdk-8-slim

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

EXPOSE 8080

CMD ["java", "-jar", "/home/app/target/authentication-service-1.0.0.jar"]