# Phoebus Software Coding Challenge

### Notes

**Database**

For simplicity, I have used the H2 database as it's in-memory and embedded in SpringBoot so no need to start a DB in another process

**ORM**

I am not sure if the challenge expects an ORM or not but I am using JPA/Hibernate so there are object relations between Customer and Account instead of storing collections of IDs

**Other Notes**

- Not sure what an 'objectid' data type would be. I assume this is the primary key so I am using an auto-generated identity field
- The association in the diagram states a many-to-many relationship between Customer and Account so Customers can have many Accounts and Accounts can have many customers
- I think a `List` for accounts/customers would not be best as it would allow duplicate elements so I used a `Set`
- Some parts of the implementation are not strictly RESTful, e.g. use of accountId/customerId query parameters and the mapping for '/validate' but I think this is the most pragmatic way to implement the API in this case

**Tests**

I have written tests for the 2 Controllers by way of an example. Obviously a real project would require more complete coverage.

### Assumptions

- For the operation: "Add a new account with a customer" I have assumed this means "Add a new account to an existing customer's accounts"
- For validating accounts/customers I am checking the object's properties but not the nested Accounts/Customers
- I assume Account.accountNumber should be unique but I have not implemented this

### Instructions

**Building the Project**

There are 2 scripts in the root of the project (assumes we are running on Linux)

- build-all
- run-docker

The **build-all** script builds the code and and packages into a Docker image as djs/psl. This assumes Java JDK v8 and Docker are pre-installed

**Running outside Docker**

From the project root, run:

`mvn spring-boot:run`

This uses the Spring Maven plugin to execute the project.

**Running in Docker**

The **run-docker** script starts a container using the djs/psl image

The container will be listening on port 8080

**Using the App**

*1. Add a new Account with a Customer*

`POST: /customer/{customerId}/accounts`

Replace customerId with the ID of the existing customer

Request body should contain a JSON object as follows:

`{"accountNumber":1111}`

Replace 1111 with the account number of the new account.

*2. Lookup a customer/s using an accountId*

`GET: /customer?accountId=1`

Replace 1 with the account ID to filter on

*3. Lookup an account/s using a customerId*

`GET: /account?customerId=1`

Replace 1 with the customer ID to filter on

*4. Match customer and account details to an input model*

`POST: /account/validate`

Request body should contain a JSON object as follows:

`{"accountNumber":1111}`

Replace 1111 with the account number of the account to be validated

`POST: /customer/validate`

Request body should contain a JSON object as follows:

`{"forename":"the_forename","surname":"the_surname","dateOfBirth":"yyyy-mm-dd"}`

Replace the_forename, the_surname and yyyy-mm-dd with the forename, surname and date of birth respectively of the customer to be validated

In both cases, the server should respond depending on whether the Account/Customer matches in the database:

`Account/Customer is/is not valid`


