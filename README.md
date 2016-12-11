Test web application for technical test

Prerequisites
-------------
-JDK 1.8
-Maven 3

Installation
------------

Clone repository from Github: git clone ....
Generate application: mvn install
Running test: mvn test

Execution
---------
Go to target directory:
java -jar test-web-application.jar

The web server is waiting at localhost:8000
There are 3 default users: user1/password1, user2/password2, user3/password3
Roles: user1 -> PAGE_1, ADMIN
	   user2 -> PAGE_2
	   user3 -> PAGE_3

Access to private pages
-----------------------
http://localhost:8000/app/page1
http://localhost:8000/app/page2
http://localhost:8000/app/page3

There will be an authetication form if session is needed.

Acces to Rest API
-----------------
Any role:
Get users:    GET http://localhost:8000/api/users
Get user:     GET http://localhost:8000/api/users/user/{username}

Only ADMIN role:
Create user:  POST http://localhost:8000/api/users 
			  payload -> {"username":"username", "password":password", "roles":"comma-delimitted rolenames"}
Delete user:  DELETE http://localhost:8000/users/user/{username}
Update user:  PUT http://localhost:8000/users/user/{username}
			  payload -> {"username":"username", "password":password", "roles":"comma-delimitted rolenames"}
