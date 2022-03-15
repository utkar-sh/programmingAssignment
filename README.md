# Chegg Programming Assignment

## Steps to follow to run the program

### This assignment is attempted using Spring Boot MVC framework.

### 1. Clone repository from github. 
### 2. Run the command "mvn clean install" in the root directory.
### 3. The application utilizes the port 8080, so the application can be accessed at localhost:8080.
### 4. The application utilizes in-memory DB (H2) which can be accessed at localhost:8080/h2-console.

### NOTE: the API mappings can be found in the controller files.

#### Some important highlights:
#### 1. The scope to write unit test cases in this project was limited as there are some modules which are used extensively but aren't written (external libraries). Therefore, it made more sense to me to have written Integration test cases rather than unit test cases. But this seemed to be out of scope for this assignment. 
#### 2. The findBySchoolName mapping doesn't work properly at this moment but I have attempted using @Query annotation to run it. So the code will break when those API endpoints are accessed.
