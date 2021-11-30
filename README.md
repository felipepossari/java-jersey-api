
# Java Jersey API

RESTful API created using Jersey Framework and Java 8 that allows create, update, delete and update food.

### Key features
- Create food;
- Read all food;
- Read food by food type;
- Update food;
- Request validation;
- Data validation;
- Unit test;
- Integration test;

#### Limitations

* The project does not use any kind of data storage. The repository layer is faked using a simple list of Food;
* The Get Food API does not use pagination;

### Possible improvements

* Add a data storage like MySql using docker;
* Add pagination in the Get Food API;
* Add dynamic field selection in the Get Food API;
* Add [test-containers](https://github.com/testcontainers/testcontainers-java) to improve integration test;

### Techs used
- Java 8;
- Jersey Framework 3.0.2;
- Junit 5.8.1;
- Mockito 4.0.0;
- Lombok 1.18.22;
- Grizzly HTTP server;
- HK2;

Notes:
Lombok were used to generate getters, setters and class constructors. Since Jersey uses Jakarta annotations to perform dependency injection, the HK2 were used to map the interfaces and its implementation.

## Requirements

In order to run and change the code of this project you have to install:

- Some IDE you like to use. E.g.: [Eclipse](https://www.eclipse.org/downloads/) or [Intellij](https://www.jetbrains.com/pt-br/idea/download/);
- Java [JDK 8](https://www.jetbrains.com/pt-br/idea/download/#section=linux);
- [Maven](https://maven.apache.org/download.cgi);
- [Postman](https://www.postman.com/downloads/);

Once everything installed you can follow the next steps to compile and run.

## How to run

```bash
git clone https://github.com/felipepossari/java-jersey-api.git
mvn clean test
mvn exec:java
```
The message below informs the API is ready to be used:

    INFO: Jersey app started with endpoints available at http://localhost:8080/

## API Documentation

### Entities
* Food Request:
```
{
	"name": "Orange",
	"weight": 1,
	"type": "FRUITS"
}
```
* Food Response:
```
{
	"id": "1274ae3a-2c7a-494a-bd62-1894a14d3aab"
	"name": "Orange",
	"weight": 1,
	"type": "FRUITS"
}
```
* Error Response:
```
[
	{
		"code": "R000",
		"message": "Error reason"
	}
]
```

### Create Food
* **URL**: /food
* **Method**: POST
* **Body**: Food Request
* **Success Response**:
    * **Code**: 201 Created
    * **Header**: Location => /food/{{id}}
* **Error Responses**:
    * **Code**: 400 Bad Request or 409 Conflict
    * **Content**: Error Response
* **Sample Call**:
```
curl --location --request POST 'http://localhost:8080/foods' --header 'Content-Type: application/json' --data-raw '{"name": "Orange","weight": 1,"type": "FRUITS"}'
```

### Get All Food
* **URL**: /food
* **Method**: GET
* **Success Response**:
    * **Code**: 200 OK
    * **Content**: List of Food Response
* **Sample Call**:
```
curl --location --request GET 'http://localhost:8080/foods'
```

### Get Food By Food Type
* **URL**: /food/{{type}}
* **Method**: GET
* **URL Params**: Type => VEGETABLES, FRUITS, GRAINS, MEAT, POULTRY, FISH, SEAFOOD, DAIRY_FOOD
* **Success Response**:
    * **Code**: 200 OK
    * **Content**: List of Food Response
* **Sample Call**:
```
curl --location --request GET 'http://localhost:8080/foods'
```


### Update Food
* **URL**: /food/{{id}}
* **Method**: PUT
* **URL Params**: Food Id
* **Body**: Food Request
* **Success Response**:
    *  **Code**: 204 No Content
* **Error Responses**:
    * **Code**: 400 Bad Request or 409 Conflict
    * **Content**: Error Response
* **Sample Call**:
```
curl --location --request PUT 'http://localhost:8080/foods/fe8b1932-ec79-4768-8a41-4ad1a9e4144c' --header 'Content-Type: application/json' --data-raw '{"name": "Apple","weight": 2,"type": "FRUITS"}'
```


### Delete Food
* **URL**: /food/{{id}}
* **Method**: DELETE
* **URL Params**: Food Id
* **Body**: Food Request
* **Success Response**:
    *  **Code**: 204 No Content
* **Error Responses**:
    * **Code**: 400 Bad Request or 409 Conflict
    * **Content**: Error Response
* **Sample Call**:
```
curl --location --request DELETE 'http://localhost:8080/foods/fe8b1932-ec79-4768-8a41-4ad1a9e4144c'
```

### Error Codes
|Error Code| Layer | Message  |
|--|--|--|
| R001 | API | Food name must not be null or empty |
| R002 | API | Food name must not have more than 50 characters |
| R003 |API | Food weight invalid |
| R004 |API | Food type invalid. Values: VEGETABLES, FRUITS, GRAINS, MEAT, POULTRY, FISH, SEAFOOD, DAIRY_FOOD |
| R999 |API | Unknown error |
| C001 | Application | Resource not found |
| C002 | Application | Resource already registered |

## Project structure

    .
    ├── ...
    ├── postman                                     # Postman collection    
    ├── src                    		        # Project Source Code
    │   ├── main/java/com/felipepossari/jersey      # Java classes
    │		├── adapter			# Layer responsible to exchange data with external systems
    │ 	    ├── application			# Core layer with domain classes and use cases
    │ 	    ├── configuration			# Layer responsible to hold configuration classes
    │   ├── test/java/com/felipepossari/jersey	# Unit and integration test classes
    └── ...

Thinking about Hexagonal Architecture the project was created with the idea that every data input and output should be transparent from the application layer, the core. Digging into Java classes folders we have:

    .
    ├── ...
    ├── adapter
    │   ├── in/web		# Contains the API Endpoint classes
    │   ├── out/repository	# Contains the repository classes
    └── ...
The adapter/in folder will contain every class, integration, component which allow the data input to the system. In this case the classes with the Jersey annotations that providers a REST endpoint to the system are located in the folder adapter/in/web.

The adapter/out folder will contain every class, integration, component that allows the data output of the system. The folder repository contains the in memory repository class that performs "data persistence" to the system.

    .
    ├── ...
    ├── application
    │   ├── domain			# Domain classes with data and behavior
    │   ├── exception		# Exceptions used in the business logic
    │   ├── port			# Interfaces that provides data input and output from application layer
    │		   ├── in	# Interfaces to perform data input and map use cases
    │		   ├── out	# Interfaces to perform data output
    │   ├── service			# Use cases with business logic
    └── ...

The application folder holds the core of the system. It must run the business logic independent from other layers.

The domain folder contains the Food class which has the fields name, weight and food type used in the project. It illustrates a domain object. Since this is a small project it has not behaviors but it is in the domain classes that they should be.

The exception folder contains the exception used in the project to control and validate the business flow.

The port folder has the sub folders in and out. In order to illustrate a user need, the input interfaces are created thinking about the use cases, the actions that the user could perform in the system. In the case of this project the user could create, update, read or delete foods so almost each one of this actions became a use case. These use cases were created inside port/in folder.

The port /out folder contains interfaces that enables the application to send data to outside. Usually we save the save into databases or send some information to other systems. These actions inside the business logic are performed through out put interfaces wich are called ports like RepositoryPort.

The service folder contains the implementations of the use cases interfaces with the business logic. Each use case has a separated implementation regarding the system actions.


## License
[MIT](https://choosealicense.com/licenses/mit/)
