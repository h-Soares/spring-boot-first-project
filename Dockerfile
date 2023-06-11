FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080