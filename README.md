# ResQuod Backend

[![Build Status](http://whcp.pl:3300/buildStatus/icon?job=ResQuodBackend)](http://whcp.pl:3300/job/ResQuodBackend/)
[![Build Status](https://img.shields.io/github/issues/UStudentGit/ResQuodBackend)](https://github.com/UStudentGit/ResQuodBackend/issues)

Open [Swagger](http://whcp.pl:3200/swagger-ui.html#/)

### Environments
http://whcp.pl:3200/

## Requirements

For building and running the application you need:

- [JDK 14](https://jdk.java.net/14/)
- [Maven 3](https://maven.apache.org)

## Running the application locally
1. Clone the repository
2. Open the project in Intellij IDEA
3. Install MariaDB
4. Create empty database in MariaDB
5. Initial Environment variables
Windows
```
set DATABASE_URL=jdbc:mariadb://localhost:3306/database
set DATABASE_USER=user
set DATABASE_PASSWORD=password
```
Unix
```
export DATABASE_URL=jdbc:mariadb://localhost:3306/database
export DATABASE_USER=user
export DATABASE_PASSWORD=password
```

# Working with the project
Start app using docker
```
docker build . -t resquodbackend
docker run -d --publish 3200:3200 --name resquodbackend --env DATABASE_USER --env DATABASE_PASSWORD --env DATABASE_URL resquodbackend
```
Start app using Maven
```
mvn clean build
java -jar target/*.jar
```
or run the main class from IDE, don't forget to setup environment variables.