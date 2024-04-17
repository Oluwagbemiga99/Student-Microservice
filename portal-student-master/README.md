# Portal Student

## Integrations
### 1. Database
The application integrates with a PostgreSQL relational database.<br/>
Scripts to create the database schema can be found in the migrations folder.

1. Download and install PostgreSQL https://www.postgresql.org/download/
2. Create database with name **portal_student**
3. run scripts in the migrations folder to create tables.

### 2. Finance
The application integrates with the [Finance microservice](https://github.com/tvergilio/finance) via REST.
1. When a student is created, a request is sent to the Finance microservice to create an account.
2. When you enrol in a course, a request is sent to the Finance microservice to create an invoice.
3. Upon checking the eligibility to graduate, a request is sent to the Finance microservice to see if there are any outstanding invoices.

### 3. Library
The application integrates with the [Library microservice](https://github.com/AidanCurley/CESBooks) via REST.
1. When a student is created, a request is sent to the Library microservice to create an account.

## How to Run
1. go to src/main/resources/application.properties, change the database username and password
   which has been created
2. mvn clean install
3. mvn spring-boot:run

## Test using Postman
Download Postman from https://www.postman.com/ and import the collections found in the `/postman` directory.

