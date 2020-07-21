# BankingSystem

Spring-boot application to simulate a banking system, with different types of users and accounts, transactions and fraud detection

## Notes:
Project is in the folder server. Folder client is the React app I'm developing for the front-end but didn't have the time to finish for now.
After running the program, open http://localhost:8080/swagger-ui.html to try all the endpoints. 
First of all, you have to sign in as a admin, account holder or third party. You have 2 routes to sign in, if you are an admin or account holder use the first route
and enter your email and password, if you are a third party user, sign in with your email and secret. You can use one of examples that I inserted in the database,
or you can start to create an admin(this route is non secure to be possible for you to create), and after that sign in with the admin and create the other users.
When you sign in you will receive your auth token since I'm doing the authentication based in JSON Web Tokens. From now on use the type of token and your token 
in the field authorization that appears in swagger, for example:

Bearer token

When you sign in with a different user don't forget to change the token.

## Functionalities:
Depending on the Roles: ADMIN, ACCOUNT_HOLDER AND THIRD PARTY

- Create diferent types of account: checking, savings or credit account {ADMIN}
- See information of all accounts, accounts filtering by type, accounts of one specific user or one speficic account {ADMIN}
- See information of self accounts, one speficic account or accounts filtering by type {ACCOUNT_HOLDER}
- Credit and debit to/from one account {ADMIN, THIRD_PARTY}
- Execute a transaction {ACCOUNT_HOLDER}

## Schedule tasks:
All the fees and interest rates associated to an account are being charged with spring schedule tasks.
So every day my banking system will run these tasks:
- credit the interest rate to savings accounts
- debit the interest rate from credit accounts
- debit the monthly fee from regular checking accounts
- debit the penalty fee if any regular checking or savings account drops below the minimumBalance

Implementation logic: when an account is created the first charge date is saved, and since these schedule tasks are going to run every day, when the current date
is the same of the date that we have to charge, it will charge and update the current date.

## Authentication:
I decided to implement JWT(JSON Web Tokens) authentication instead of basic authentication. Explained in the diagram how it works.


![JWTAuthDiagram](https://i.ibb.co/RDw2TSM/Captura-de-ecra-2020-06-27-a-s-00-31-27.png)


## Authorizathion:
Almost all the routes have a pre-authorize, but some depends. For example I authorize an user with the role ACCOUNT_HOLDER to see the balance of his own accounts, but I just check if the account belongs to him inside of the method in the service.

## Exceptions and Validations:
I'm handling all exceptions for not found, not authorized, not enoughs funds, invalid inputs. For validations I'm using Bean Validation 2.0, this has a Java Validation API, fields that can't be null, that have to be postive, number of integer and decimal parts in BigDecimal values, patterns to match a phone number and email.

## DTOs:
Data Transfer Objects were implemented to map from the models objects to the objects I wish to return to the user(to not return unecessary information), to update partially and to insert new data.
