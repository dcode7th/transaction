# Account relative balance
The solution for relative account balance has 2 application layers:

1. Service - orchestration logic including business rules
2. Repository - data access logic

Assumptions:
1. No API layer i.e., REST required to be exposed
2. Interact data directly from the CSV file for simplicity. In reality, the transaction data in CSV file can be extracted, transformed and loaded to database (ETL)
3. For toDate in the input argument, if hour and minute are 0, it is implied that the hour and minute are 59. i.e., toDate is 10:00:00, the updated toDate will be 10:59:59

