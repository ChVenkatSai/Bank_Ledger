
Bank-Ledger
===============================

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service that has been implemented.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT returns the updated transactionAmount following the transaction. Authorization declines are also saved, even if they do not impact transactionAmount calculation.

The event sourcing pattern to records all banking transactions as immutable events. Each event captures relevant information such as transaction type, amount, timestamp, and account identifier.

The Database is MongoDB. The user balances and transactions are both stored as collections in the same database. 

# README
## Bootstrap instructions
To run this server locally, clone the repository.
Navigate to the project directory and run the application with the command

mvn spring-boot:run

Setup the MongoDB Server.
Curl to port 8080 with the respective schema or use Postman.  


## Design considerations
The following is the design of the application:

![Alt text](um.PNG)

## Assumptions
I assumed that the amount is given in such a way that the balance amount never goes above the maximum limit of double.
Working with double may introduce slight errors in calculation but this should not affect the results
as the errors are generally in later places of decimals. We can bypass double implementation by performing 
string additions rather than double additions or having types that can store larger numbers. Again these
implementations inject problems of their own. 



