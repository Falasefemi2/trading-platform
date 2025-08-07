# Trading Platform

This is a simple trading platform application built with Spring Boot. It allows users to create, retrieve, update, and delete trades.

## Technologies Used

* Java 21
* Spring Boot 3.5.4
* Spring Web
* Spring Data JPA
* Hibernate Validator
* MySQL Connector/J
* Lombok
* Maven

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* JDK 21 or later
* Maven 3.6.3 or later
* MySQL 8.0 or later

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Falasefemi2/trading-platform.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd trading-platform
   ```

3. **Configure the database:**

   Open `src/main/resources/application.properties` and update the following properties with your MySQL database credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/trading_platform
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run the application:**

   ```bash
   ./mvnw spring-boot:run
   ```

The application will be available at `http://localhost:8080`.

## Running Tests

To run the tests, execute the following command:

```bash
./mvnw test
```

## API Endpoints

The following API endpoints are available:

| Method | Endpoint         | Description                  |
| ------ | ---------------- | ---------------------------- |
| GET    | `/api/trades`    | Get all trades               |
| POST   | `/api/trades`    | Create a new trade           |
| PUT    | `/api/trades/{id}` | Update a trade               |
| DELETE | `/api/trades/{id}` | Delete a trade               |

## Request Body

The following is an example of the request body for creating or updating a trade:

```json
{
  "symbol": "AAPL",
  "quantity": 100
}
```

## Error Handling

The application handles errors and returns appropriate HTTP status codes and error messages.

### Trade Not Found

If a trade with the specified ID is not found, the application returns a `404 Not Found` status code with the following response body:

```json
{
  "error": "Trade not found with id: 1"
}
```

### Validation Errors

If the request body is invalid, the application returns a `400 Bad Request` status code with a response body containing the validation errors:

```json
{
  "symbol": "Symbol is mandatory",
  "quantity": "Quantity must be at least 1"
}
```

## Future Features

* **Security:** Implementation of security features to protect the API endpoints.
* **AI-Powered Live Stock Updates:** Integration of an AI-powered service to provide real-time stock updates.

## Database Configuration

The application uses MySQL as its database. The database connection properties are configured in the `src/main/resources/application.properties` file.

By default, the application uses the following database configuration:

* **URL:** `jdbc:mysql://localhost:3306/trading_platform`
* **Username:** `root`
* **Password:** `password`

You can change these properties to match your local MySQL installation.

## How to Contribute

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request
