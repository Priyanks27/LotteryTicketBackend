# LotteryTicketBackend

Download the project using github link. 

Assumptions:
1) The number of lines in a tickket N, is constant across the system, there is a provision to change it in the application.properties file : app.Number_of_lines_on_ticket
2) Digits in each line can be updated from using application properties value of : app.digits_in_a_line
3) The values of the digits can be updated using application properties value of : app.Values
4) New classes need to be added for adding rules, though priority of existing rules and their 
5) The requests coming for the tickets to be created and load should be within 100 requests

Pre-requisites:

1) Java and JDK must be setup and JAVA_HOME must be added in the path. Project can be explored and debugging can be done using IntelliJ or eclispe. Open the pom.xml as a project in IntelliJ. Download Java: https://java.com/en/download/ , https://www.oracle.com/java/technologies/javase-downloads.html
2) Cassandra version : apache-cassandra-3.11.4 was installed on the development environment. Python 2.7 is installed for cassandra to function. https://cassandra.apache.org/download/
3) start cassandra by running cassandra.bat from the ~\cassandra\bin location.
4) Alternatively, execute following instructions to setup cassandra for this project:

  a) docker pull cassandra latest
  b) docker run --name cassandra -p 9042:9042 -d cassandra:3.0
  c) docker ps
  d) docker exec -it cassandra bash
  e) cqlsh
  f) create KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};


Go to the base directory and Use following instructions to launch the project

1. mvn package
2. mvn clean install
3. mvn exec:java -Dexec.mainClass=com.interveiw.poppulo.lotteryticket.LotteryticketApplication

Integration Junit tests will automatically execute and the application will start.
After successfully starting the server, launch the application using: https://localhost:8443/swagger-ui.html#


Functionalities: 
1) Create Lottery ticket
2) Get All tickets 
3) Get tickets by Id
4) Update ticket by Id
5) Update and get status of the ticket which the locked and cannot be amnened further
6) Sperarte rule engine to add the rules
7) Rules and their priority defined in application.properties file
8) Configurable system with junit integration tests
