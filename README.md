# Test web application for technical test

## Prerequisites

1. JDK 1.8
2. Maven 3

## Installation

1. Clone repository from Github: https://github.com/dralluy/test-web-application.git
2. Generate application: mvn install
3. Running test: mvn test

## Execution

1. Go to target directory
2. java -jar test-web-application.jar

The web server is waiting at localhost:8000
There are 3 default users: user1/password1, user2/password2, user3/password3
Roles: user1 -> PAGE_1, ADMIN
	   user2 -> PAGE_2
	   user3 -> PAGE_3

## Access to private pages

http://localhost:8000/app/page1
http://localhost:8000/app/page2
http://localhost:8000/app/page3

There will be an authetication form if session is needed.

## Acces to Rest API

Any role:
1. Get users:    GET http://localhost:8000/api/users
2. Get user:     GET http://localhost:8000/api/users/user/{username}

Only ADMIN role:
1. Create user:  POST http://localhost:8000/api/users 
	      payload -> {"username":"username", "password":password", "roles":"comma-delimited rolenames"}
2. Delete user:  DELETE http://localhost:8000/users/user/{username}
3. Update user:  PUT http://localhost:8000/users/user/{username}
			  payload -> {"username":"username", "password":password", "roles":"comma-delimited rolenames"}
