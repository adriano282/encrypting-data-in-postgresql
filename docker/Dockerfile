FROM openjdk:8-jre-alpine
MAINTAINER Adriano Jesus "adriano.jesus282@gmail.com"
COPY encrypting-data-in-postgres-0.0.1-SNAPSHOT.jar /app/demoservice.jar
EXPOSE 8080
ADD configuration/demoservice/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
