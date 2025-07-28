## Spring Boot Demo Project

### Prerequisites

- Java 21 (JDK 21)
- Maven 4
- MySQL 8.x

### Database Configuration

The application expects a MySQL database with the following settings:

- **Database name:** `shopcartdb`
- **Username:** `admin`
- **Password:** `adminpass`
- **Host:** `localhost`
- **Port:** `3306`

You can configure these in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/shopcartdb
spring.datasource.username=admin
spring.datasource.password=adminpass
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

#### Creating the Database Manually

Run these SQL commands in your MySQL client:

```sql
CREATE DATABASE shopcartdb;
CREATE USER 'admin'@'%' IDENTIFIED BY 'adminpass';
GRANT ALL PRIVILEGES ON shopcartdb.* TO 'admin'@'%';
FLUSH PRIVILEGES;
```

### Running Locally

1. Make sure MySQL is running and the database/user are created as above.
2. Build the project:
   ```sh
   mvn clean package
   ```
3. Run the application:
   ```sh
   java -jar target/springbootdemo-0.0.1-SNAPSHOT.jar
   ```

### API Testing with Postman

- A Postman collection JSON file is included in the project (see `ShoppingCartTest.postman_collection.json`).
- Database table should be empty
- Import it into Postman to test all endpoints.

### Notes

- Default users and roles are loaded from `src/main/resources/users.csv`.
