# BankingSystem

## Notes
After running the program, open http://localhost:8080/swagger-ui.html to try all the endpoints. 
First of all, you have to sign in as a admin, account holder or third party. You have 2 routes to sign in, if you are an admin or account holder use the first route
and enter your email and password, if you are a third party user, sign in with your email and secret. You can use one of examples that I inserted in the database,
or you can start to create an admin(this route is non secure to be possible for you to create), and after that sign in with the admin and create the other users.
When you sign in you will receive your auth token since I'm doing the authentication based in JSON Web Tokens. From now on use the type of token and your token 
in the field authorization that appears in swagger, for example:
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTkzNDA3NzYyLCJleHAiOjE1OTQwMTI1NjJ9.RUmv9Ei_gTypF2Q6ZkuJ-xOzQm3QZYsozU4TvpA8SHA7K5NxeBmcteAQBZnXnT46tFf5JmBPpoCPYmpDwguH_Q
When you sign in with a different user don't forget to change the token.

## Functionalities:
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
I decided to implement JWT(JSON Web Tokens) authentication instead of basic authentication.

Roles:
ADMIN, ACCOUNT_HOLDER AND THIRD PARTY
