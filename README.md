# Virtual Book Store API

A Spring Boot API for managing a virtual book store with user authentication and role-based access control.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build and Run](#build-and-run)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Models](#models)

 ## Prerequisites

Before running this project, ensure that you have the following installed:

- Java Development Kit (JDK) [Download](https://www.oracle.com/java/technologies/javase-downloads.html)
- Maven [Download](https://maven.apache.org/download.cgi)
- MySQL Database [Download](https://dev.mysql.com/downloads/)
- Your preferred IDE (e.g., IntelliJ, Eclipse).

## Getting Started

### Clone the Repository
git clone https://github.com/your-username/virtual-book-store-api.git   
cd virtual-book-store-api(folder the repository was cloned).

### Build and Run
- mvn spring-boot:run.   
- The API will be running at http://localhost:8080.

## API Endpoints
### Login:
- Endpoint: POST /api/auth/login.   
- Description:  Authenticates a user and returns a JWT token.

### SignUp:
- Endpoint: POST /api/auth/signup.   
- Description: Creates a new user account.

### Get Book:
- Endpoint: GET /api/books/{bookId}.   
- Description: Retrieves details of a specific book.

### Update Book (Admin Only):
- Endpoint: PUT /api/books/{bookId}
- Description: Updates details of a specific book. Requires admin privileges.

### Search by Author:
- Endpoint: GET /api/books/search?author={authorName}
- Description: Searches for books by a specific author.

### Add Review:
- Endpoint: POST /api/review/add-review
- Description: Adds a review for a specific book.

### Remove Book (Admin Only):
- Endpoint: DELETE /api/books/{bookId}
- Description: Removes a specific book. Requires admin privileges.

## Authentication
This API uses JWT authentication with OAuth2 Resource Server. Make sure to include the JWT token in the Authorization header for secured endpoints.

## Models
### Book:
Attributes: Title, Author, Category.      

### Review:
Attributes: User, Book, Rating, Comment, etc.    

### Role:
Attributes: Name (e.g., ROLE_USER, ROLE_ADMIN).   

### User:
Attributes: Username, Password, Roles.


