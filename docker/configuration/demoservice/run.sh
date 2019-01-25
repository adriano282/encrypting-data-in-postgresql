#!/bin/sh
echo "********************************************************"
echo "Waiting for the database server to start on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! `nc -z database $DATABASESERVER_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Starting Demo Server"
echo "********************************************************"
ENCRYPT_KEY=$ENCRYPT_KEY  java -jar /app/demoservice.jar
