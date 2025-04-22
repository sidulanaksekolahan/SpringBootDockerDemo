# Basic Spring Boot with Docker

## How to run this project with maven:
### 1. Create a jar with the following command:
```
mvn clean package
```
### 2. Run the generated jar
```
java -jar target\SpringBootDockerDemo-0.0.1-SNAPSHOT.jar
```
#### By default the application will run on port 8080. 
#### So go to the http://localhost:8080/api/v1/test and you will see the result