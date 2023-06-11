FROM openjdk:17-alpine
VOLUME /tmp
COPY target/spring-user_department-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080