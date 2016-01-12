Instructions to project

TEST:
To see the results of the requests download Firefox plugin: Restclient  (example img: putExample.png)

SAVED:
Transactions saved in file transactions.txt (Project_path/transactions.txt)

CONFIGURE:
Important: change absolute path - pathAbsolute variable

STRUCTURE AND EXAMPLES:
PUT :  http://localhost:8080/Restfuldynamic/transactionservice/transaction/$transacion_id/$params
Prerequisite : Headers - Content-Type: text/html 
Example : http://localhost:8080/Restfuldynamic/transactionservice/transaction/20/{"amount":"270.0","parent_id":212344,"type":"car"}

GET :  http://localhost:8080/Restfuldynamic/transactionservice/transaction/$transaction_id
Example: http://localhost:8080/Restfuldynamic/transactionservice/transaction/16

GET :  http://localhost:8080/Restfuldynamic/transactionservice/sum/$transaction_id
Example: http://localhost:8080/Restfuldynamic/transactionservice/sum/16

GET :  http://localhost:8080/Restfuldynamic/transactionservice/types/$type
Example: http://localhost:8080/Restfuldynamic/transactionservice/types/bag