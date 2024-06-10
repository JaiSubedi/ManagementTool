# Tool Rental Management System

This project is a Tool Rental Management System that provides two main entry points for users to interact with the application:
1. **API-based interaction using Swagger**.
2. **Web-based interaction using Thymeleaf templates**.

The project also includes comprehensive test cases to ensure the correctness of the rental agreement logic.

## Tools and Technologies Used
- **Java**: Core language for development.
- **Spring Boot**: Framework used for building the application.
- **H2 Database**: In-memory database for storing tool and rental agreement data.
- **Hibernate Validation**: For validating input data at both service and controller levels.
- **Thymeleaf**: Templating engine for rendering web pages.
- **Swagger**: For API documentation and testing.
- **JUnit and Mockito**: For writing and running test cases.

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven

### Steps to Run

1. **Import the project in an IDE like Intellij**

2. **Build the project**
    ```bash
    ./mvnw clean install
    ```

3. **Run the application**
    ```bash
    ./mvnw spring-boot:run
    ```
You can also start the application by executing the class [`ToolmanagementApplication`](src/main/java/com/test/toolmanagement/ToolmanagementApplication.java) class. 
The application runs on port `8080`.

## Accessing the Application

### 1. API-based Interaction using Swagger
You can interact with the application APIs using Swagger. The Swagger UI is accessible at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 2. Web-based Interaction using Thymeleaf
You can interact with the application through a web interface. The entry point for the web interface is:
[http://localhost:8080/rental/form](http://localhost:8080/rental/form)

## Application Controllers

### API Controller

The API controller provides endpoints to interact with the rental system programmatically.

**Endpoint: /api/rentals/checkout**

- **Method**: POST
- **Description**: Checkout a tool and create a rental agreement.
- **Request Body**:
  ```json
  {
    "toolCode": "string",
    "rentalDays": "integer",
    "discountPercent": "integer",
    "checkoutDate": "string (yyyy-MM-dd)"
  }
    ```
- **Response Body**:
    ```json
    {
      "toolCode": "string",
      "toolType": "string",
      "toolBrand": "string",
      "rentalDays": "integer",
      "checkoutDate": "string (mm/dd/yyyy)",
      "dueDate": "string (mm/dd/yyyy)",
      "dailyRentalCharge": "number",
      "chargeDays": "integer",
      "preDiscountCharge": "number",
      "discountPercent": "integer",
      "discountAmount": "number",
      "finalCharge": "number"
    }
   ```
### Web Controller

The web controller provides endpoints to interact with the rental system via a web browser using Thymeleaf templates.

**Endpoints:**

1. **/rental/form**
    - **Method**: GET
    - **Description**: Display the rental form.

2. **/rental/receipt**
    - **Method**: POST
    - **Description**: Generate a rental agreement receipt.
    - **Form Data**:
        - `toolCode`: Tool code (string)
        - `rentalDays`: Rental days (integer)
        - `discountPercent`: Discount percent (integer)
        - `checkoutDate`: Checkout date (string, yyyy-MM-dd)

### Test Cases

The project includes JUnit test cases to validate the rental agreement logic under various scenarios. These tests ensure the correctness of the service layer by simulating different input conditions and verifying the expected outputs.

### Running the Test Cases

To run the tests, use the following command:
```bash
./mvnw test
```

These test cases are present in  [`RentalServiceTest`](src/test/java/com/test/toolmanagement/service/RentalServiceTest.java) class..