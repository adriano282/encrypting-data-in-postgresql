
# encrypting-data-in-postgresql

This is a customer Rest API (POST and GET) example project to show how to encrypt data in PostgreSQL databases.

The encryption is done through a PostgreSQL extension called PGCrypto [https://www.postgresql.org/docs/10/pgcrypto.html].

# How does the application work?

### There is a docker-compose file containing:

 - a PostgreSQL database;
 - Adminer to access the PostgreSQL database:
    Accsss Info:
      Server	  - "database"
      Username	- "demo"
      Password	- "demo"
      Database	- "demo"
 - Container for the application itself
 
 # How does the encryption work?

 ### PGCrypt extension used functions
 
 Once enabled, the PGCrypto extension provide a set of database function.
 The function used for encryptio are:
  - pgp_pub_encrypt = encrypts data
  - pgp_pub_decrypt = decrypts data
  - dearmor         = parses a generated GPG keys to the fuctions above
 
 ### GnuPG to generate keys for encryption
  The private and public keys were generated using GnuPG [https://www.gnupg.org/] which is an implementation of the OpenPGP standard.
 
 The PGCrypt functions are applied to entities' columns which bear sensitive information. In this example, the columan is a secret representing a possible access customer.
 
 ### Columns Mapping with Hibernate @ColumnTransformer
 
 The columns intended to be encrypted should be annotated with Hibernate's @ColumnTransformer [https://docs.jboss.org/hibernate/orm/4.3/javadocs/org/hibernate/annotations/ColumnTransformer.html] annotation.
 
 
# Running the integration tests

./gradlew integrationTest

# How to Run the application

./gradlew clean build

docker-compose -f docker/docker-compose.yml up

## Endpoints available

GET /customer/{id}
POST /customer

# Sample requests

curl -H "Content-Type: application/json" -X POST localhost:8080/customer -d '{ "name": "Adriano de Jesus", "username": "adriano.jesus2", "password": "my-super-secret-password" }'  

curl localhost:8080/customer/1

# Accessing the Adminer

http://localhost:5433


### Some References

https://github.com/michael-simons/simple-meetup/blob/master/build.gradle
https://github.com/tomasulo/docker-compose-integration-tests/blob/master/build.gradle
https://cloud.spring.io/spring-cloud-cli/
