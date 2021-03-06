
# encrypting-data-in-postgresql

This is a customer Rest API (POST and GET) example project to show how to encrypt data in PostgreSQL databases.

The encryption is done through a PostgreSQL extension called PGCrypto [https://www.postgresql.org/docs/10/pgcrypto.html].

# How this example is structured?

### There is a docker-compose file containing:

 - a PostgreSQL database
 
 - Adminer [http://localhost:5433] for accessing the PostgreSQL database in brownser
    
    Access info: \
       Server	  - "database" \
       Username	- "demo"     \
       Password	- "demo"     \
       Database	- "demo"     
       
 - docker container for running the application
 
 # How does application deal with cryptography?
 
 ### PGCrypt extension functions
 
 Once enabled, the PGCrypto extension provide a set of database function.
 
 The used functions are:
  - pgp_pub_encrypt = encrypts data
  - pgp_pub_decrypt = decrypts data
  - dearmor         = parses a generated GPG keys to the fuctions above
 
 The PGCrypt functions are applied to entities' columns which bear sensitive information. In this example, the column is a secret representing a possible access customer.
 
 ### GnuPG to generate keys for cryptography
  The private and public keys were generated using GnuPG [https://www.gnupg.org/] which is an implementation of the OpenPGP standard.
 
 ### Entity column mapping with Hibernate @ColumnTransformer
 
 The columns intended to be encrypted should be annotated with Hibernate's @ColumnTransformer [https://docs.jboss.org/hibernate/orm/4.3/javadocs/org/hibernate/annotations/ColumnTransformer.html] annotation.
 
 ### Application properties decryption with Spring Cloud Config lib
 
 The keys and password used for encrypting data in database written in application.properties file were encrypted using Spring Cloud Config Cli. [https://cloud.spring.io/spring-cloud-cli/]
 
 The properties are decrypted at runtime by Spring Cloud Config lib dependency. These properties were marked with {cipher} before the value to signalize the Spring Cloud Config to decrypt them.
 
 ### Enabling the PGCrypto extension
 
 In this project, the PGCrypto is already enabled for you. But for informative purposes, to enable the PGCrypto in PostgreSQL, you need only to run the following SQL command:
 
 CREATE EXTENSION PGCRYPTO;
 
## Running the integration tests

./gradlew clean integrationTest

## How to Run the application

./gradlew clean build

docker-compose -f docker/docker-compose.yml up

## Endpoints

GET /customer/{id} \
POST /customer

## Sample requests

curl -H "Content-Type: application/json" -X POST localhost:8080/customer -d '{ "name": "Adriano de Jesus", "username": "adriano.jesus2", "password": "my-super-secret-password" }'  

curl localhost:8080/customer/1


### Some References

https://github.com/michael-simons/simple-meetup \
https://github.com/tomasulo/docker-compose-integration-tests \
https://cloud.spring.io/spring-cloud-cli/
