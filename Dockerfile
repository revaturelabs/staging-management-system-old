FROM openjdk:8
EXPOSE 80
COPY target/scheduler.jar /app.jar
ENTRYPOINT java -jar app.jar
