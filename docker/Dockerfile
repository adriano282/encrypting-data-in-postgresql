FROM openjdk:8-jre-alpine
MAINTAINER Adriano Jesus "adriano.jesus2@fatec.sp.gov.br"
COPY encrypting-data-in-postgres-0.0.1-SNAPSHOT.jar /app/demoservice.jar
EXPOSE 8080
ADD configuration/demoservice/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh