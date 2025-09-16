# Spring Boot 3 Project for adding and managing Devices

## 🔧 Tech Stack
- Java 21
- Spring Boot 3.4.4
- Maven 3.9.9
- PostgreSQL 17
- Docker 28 (for containerized runs and Testcontainers)

🚀 Getting Started
- Run the application locally
- mvn clean install
- mvn spring-boot:run


📁 Key Dependencies
- spring-boot-starter-web – REST API support
- spring-boot-starter-data-jpa – ORM and database access
- springdoc-openapi-ui – Swagger UI for API docs
- postgresql – PostgreSQL JDBC driver
- lombok – Reduces boilerplate in code
- spring-boot-starter-test – Unit and integration testing
- testcontainers-postgresql – Containerized integration tests with PostgreSQL


🧪 Running Tests
- mvn test


🐳 Containerized Setup with Docker Compose

📂 File Structure
- docker-compose file expects all the below files in the project root.
- Place the following files in your project root (same level as pom.xml):
1. Dockerfile 
2. docker-compose.yml and 
3. deviceEntity-0.0.1-SNAPSHOT.jar

  
- Commands:
- docker-compose build
- docker-compose up

🔌 Sample APIs
- Get All Devices
- Request:
- GET /v1/deviceEntities/getAllDevices

  
Response:
- 
[{
"id": 78,
"name": "mobile",
"brand": "ABC",
"state": "IN_USE",
"creationTime": "2025-04-25T14:11:06.634885"
},
{
"id": 82,
"name": "Modem",
"brand": "ABC",
"state": "AVAILABLE",
"creationTime": "2025-04-25T15:48:31.064823"
}]

- API documentation:
- More endpoints can be explored via the http://localhost:1214/swagger-ui.html
