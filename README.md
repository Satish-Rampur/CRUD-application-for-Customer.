# Customer Management CRUD Application
## Project Description
This is a CRUD (Create, Read, Update, Delete) application for managing customer data. The application includes user authentication using JWT and integrates with a remote API to fetch customer data. The backend is developed using Spring Boot with a MySQL database, while the frontend is built using HTML, CSS, and JavaScript.

## Features
1. **Authentication**
    - Admin login using JWT authentication.
    - Bearer token authorization for subsequent API calls.

2. **Customer Management**

    - Create a new customer.
    - Update an existing customer.
    - Get a list of customers with pagination, sorting, and searching.
    - Get a single customer by ID.
    - Delete a customer.

3. **Sync Functionality**
     - Sync button to fetch and update customer data from a remote API.
## Technologies Used
  - Backend: Spring Boot, MySQL
  - Frontend: HTML, CSS, JavaScript
  - Authentication: JWT (JSON Web Token)

## API Endpoints
### Authentication API
  - **Path:** http://localhost:8080/admin/generateToken
  - **Method:** POST
  - **Request Body:**
      ```json
      {
          "userName": "admin",
          "password": "password"
      }

  - **Response:**
      ```string
        your_token
      ```

### Customer APIs 
- **Create Customer**

    - **Path:** http://localhost:8080/customer/add
- **Method:** POST
- **Request Body:**
    ```json
      {
        "firstName": "Jane",
        "lastName": "Doe",
        "street": "Elvnu Street",
        "address": "H no 2",
        "city": "Delhi",
        "state": "Delhi",
        "email": "sam@gmail.com",
        "phone": "12345678"
      }
Update Customer

Path: /api/customers/{id}
Method: PUT
Request Body: Same as the create customer request body.
Get Customer List

Path: /api/customers
Method: GET
Parameters: Pagination, sorting, and searching parameters.
Get Single Customer

Path: /api/customers/{id}
Method: GET
Delete Customer

Path: /api/customers/{id}
Method: DELETE
Sync Customers

Path: /api/customers/sync
Method: POST
Frontend Screens
Login Screen: User authentication form.
Customer List Screen: Displays a list of customers with pagination, sorting, and searching functionalities. Includes a Sync button to fetch and update customer data from a remote API.
Add Customer Screen: Form to create a new customer.
Setup and Installation
Prerequisites
Java Development Kit (JDK)
MySQL Database
Node.js (for running frontend code)
Backend Setup
Clone the repository:

bash
Copy code
git clone https://github.com/your-username/customer-crud-app.git
cd customer-crud-app
Configure the MySQL database:

Create a new database named customer_db.
Update the application.properties file with your MySQL database credentials.
Build and run the Spring Boot application:

bash
Copy code
./mvnw spring-boot:run
Frontend Setup
Navigate to the frontend directory:

bash
Copy code
cd frontend
Open the index.html file in your browser to access the application.

Running the Application
Access the login screen at http://localhost:8080/login.
Use the credentials test@sunbasedata.com / Test@123 to log in.
Navigate through the customer management screens to create, update, view, and delete customers.
Sync Functionality
Click the "Sync" button on the Customer List screen to fetch customer data from the remote API.
The application will authenticate using the provided credentials, retrieve the customer data, and update the local database.
Repository Structure
css
Copy code
customer-crud-app/
├── backend/
│   ├── src/
│   └── ...
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
├── README.md
└── ...
