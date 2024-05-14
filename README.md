
Bank-Ledger
===============================

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service that has been implemented.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT returns the updated transactionAmount following the transaction. Authorization declines are also saved, even if they do not impact transactionAmount calculation.

The event sourcing pattern to records all banking transactions as immutable events. Each event captures relevant information such as transaction type, amount, timestamp, and account identifier.

# README
## Bootstrap instructions
To run this server locally, clone the repository.
Navigate to the project directory and run the application with the command

mvn spring-boot:run

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
I've performed basic error handling in the requests but for intricate cases such as only allowing
valid currency, we need more logic. I'm assuming I don't need to bother about these cases for now. 
I'm also assuming I don't need to handle concurrency for now. 

## Deployment considerations
To deploy the application on for instance, let's say AWS, 

EC2 Instances: Provision EC2 instances to host our application, ensuring that we select 
appropriate instance types and configure security groups to allow HTTP traffic.

VPC Configuration: Set up a Virtual Private Cloud (VPC) to define our network
environment, including security groups for controlling inbound and outbound traffic.

Database Consideration: Although we're using in-built data structures like hashmap
or linked list, we need to ensure that the state of the database objects remains clean 
and consistent across instances. One approach would be to implement RDS.

Application Deployment: Package our application into a JAR file and deploy it 
to EC2 instances using SSH. 

Load Balancing: Configure an Elastic Load Balancer (ELB) to distribute 
incoming traffic across multiple EC2 instances, ensuring scalability and high availability.
Setup health check ups for the same. 

Monitoring with CloudWatch: Monitor our EC2 instances, load balancers, and
other AWS resources using Amazon CloudWatch to detect and troubleshoot issues. Alerts can
be configured accordingly.
