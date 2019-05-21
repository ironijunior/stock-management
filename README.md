# stock-management
A simple Stock Management API using Java.

## Getting Started

#### Running the application
- mvn spring-boot:run
- The application run on port 8080

#### Running the tests
- mvn test

#### Docker image

To run as a Docker Image, execute in root directory:

`docker build -t stock-management . && docker run -p 8080:8080 -it --rm stock-management`

Docker repository with this image:
https://cloud.docker.com/repository/docker/ironijunior/stock-management


## Endpoints
#### [POST] /updateStock
This endpoint updates the current stock of a particular product. 

- On this endpoint the requests are validated to check if they aren't outdated.
- To ensure concurrency and race condition this endpoint uses a synchronized block.

#### [GET] /stock?productId={productId} 
If you send a request towards this endpoint, you get the current stock available of a particular product.

- As there is a One-To-Many relationship between Product and Stock the Product will have the current stock.

#### [GET] /statistics?time=[today,lastMonth]
When calling this endpoint, you will receive some statistics about the stocks back. The available timespans are today (midnight until now) and last month.

- To obtain the statistics two main queries are executed and combined using a DTO.
