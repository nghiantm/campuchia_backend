# Campuchia Web Service

## Table of Contents

1. [Current Features](#current-features)
    - [User Management](#user-management)
    - [Group Management](#group-management)
2. [Prerequisites](#prerequisites)
3. [Installation](#installation)
    - [Clone the Repository](#clone-the-repository)
    - [Get ENV File](#get-env-file)
    - [Run](#run)
4. [API Documentation](#api-documentation)
    - [Accessing Swagger UI](#accessing-swagger-ui)
    - [API Security](#api-security)

## Current Features

### 1. User Management
- [x] Create user
- [x] Get user by ID
- [x] Update

### 2. Group management
- [x] Create group
- [ ] Update group
- [ ] Delete group
- [x] Get group by ID
- [x] Add user to group
- [x] Get groups of user
- [x] Get users of group

## Prerequisites
- Java 17 (https://www.oracle.com/java/technologies/downloads/#java17)
- Gradle 8.8

## Installation
### 1. Clone the Repository
```bash
git clone https://github.com/nghiantm/campuchia_backend.git
cd campuchia_backend
```

### 2. Get ENV file
You will need an environment file (`.env`) that contains configuration settings for your application. This file is not included in the repository for security reasons. You can obtain the `.env` file from Nghia.

Place the ENV file in:
```bash
src/main/resources
```

### 3. Run
You can run with IntelliJ for ease of mind.

## API Documentations
This project provides interactive API documentation using Swagger UI. You can use Swagger UI to explore and test the API endpoints.

### Accessing Swagger UI
Once the application is running, open your web browser and navigate to the following URL:
```text
http://localhost:8080/swagger-ui/index.html
```

### API Security
All API endpoints, except for the API docs, are secured by the Firebase Authorization Server. To obtain an idToken in testing, send a POST request to GOOGLE_IDENTITY_TOOL_KIT_URL that can be found in the ENV file, with the following body:
```text
{
    "email": "example@xvideo.com",
    "password": "examplePassword",
    "returnSecureToken": true
}
```
All request made to the campuchia_backend must have an Authorization field in their header, in the following format:
```text
### Replace idToken with yours ###
Authorization: Bearer {idToken}
```
---

## Author

**Nghia Nguyen**  
*Chef*  
Drexel University

For questions or support, please contact:
- **Email:** [mn839@drexel.edu](mailto:mn839@drexel.edu)
- **LinkedIn:** [LinkedIn](https://www.linkedin.com/in/nghiantm/)

---
