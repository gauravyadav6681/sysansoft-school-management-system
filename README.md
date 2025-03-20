### 📌 **`README.md`**  


# 🏫 School Management System

A **Spring Boot-based** School Management System with **JWT authentication, role-based access, Redis caching, Kafka event handling, and scheduled tasks**.

## 🚀 Features
- **User Authentication & Authorization**
  - JWT-based login system
  - Role-based access control (`STUDENT`, `TEACHER`)

- **RESTful APIs**
  - Manage students, teachers, courses
  - Caching for frequently accessed data (Redis)

- **Event-Driven Architecture**
  - Kafka producer & consumer for student enrollment events

- **Scheduled Tasks**
  - Automated to fetch all users and print details for every 10 mins
---

## 🛠️ Tech Stack
- **Spring Boot** (REST APIs, Security, JPA)
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (MySQL)
- **Spring Boot Starter Cache** (Redis Caching)
- **Spring Kafka** (Event-driven messaging)
- **Spring Task** (Task scheduling)
- **GitHub Actions** (CI/CD integration)

---

## 📂 Project Structure
```
school-management-system/
│── src/
│   ├── main/
│   │   ├── java/com/example/sms/
│   │   │   ├── config/           # Security & App Configurations
│   │   │   ├── controller/       # REST API Controllers
│   │   │   ├── entity/            # Entity Models
│   │   │   ├── repository/       # JPA Repositories
│   │   │   ├── service/          # Business Logic
│   │   │   ├── event/            # Kafka Event Handling
│   │   │   ├── scheduler/        # Scheduled Tasks
│   ├── resources/
│   │   ├── application.properties  # Configuration File (Use Environment Variables)
│── pom.xml                         # Maven Dependencies
│── README.md                       # Documentation

```

---

## 🔑 Authentication & Roles
**Roles Available:**
- `STUDENT` – View students, courses
- `TEACHER` – Manage students, upload courses

**JWT Authentication:**
- Register a user → Get JWT Token
- Use JWT Token for subsequent API requests

---

## 📡 API Endpoints (Sample cURL Requests)

### **🔐 Authentication**
#### ➤ **Register a User**
```sh
curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
           "username": "john_doe",
           "email": "john@example.com",
           "password": "password123",
           "role": "STUDENT"
         }'
```
#### ➤ **Login & Get JWT Token**
```sh
curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
           "username": "john_doe",
           "password": "password123"
         }'
```
_Response:_  
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

---

### **🧑‍🎓 Student APIs**
#### ➤ **Get All Students** _(Cached using Redis)_
```sh
curl -X GET http://localhost:8080/api/students \
     -H "Authorization: Bearer <your_jwt_token>"
```

#### ➤ **Enroll Student (Triggers Kafka Event)**
```sh
curl -X POST http://localhost:8080/api/studnets/add \
     -H "Authorization: Bearer <your_jwt_token>" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "john_doe",
           "email": "john@example.com",
           "password": "password123",
           "role": "STUDENT"
         }'
```

---

### **📚 Teacher APIs**
#### ➤ **Get All Teachers** _(Cached using Redis)_
```sh
curl -X GET http://localhost:8080/api/teachers \
     -H "Authorization: Bearer <your_jwt_token>"
```

#### ➤ **Create a Course**
```sh
curl -X POST http://localhost:8080/api/courses \
     -H "Authorization: Bearer <your_jwt_token>" \
     -H "Content-Type: application/json" \
     -d '{
          
           "title": "web Development course",
           "description": "Learn the fundamentals of web Development."
         
         }'
```

---

## ⚙️ Environment Variables (Mandatory)
Before running the app, set the following environment variables:

```sh
export DB_URL="jdbc:mysql://your-db-host:port/your-db-name"
export DB_USERNAME="your-db-username"
export DB_PASSWORD="your-db-password"
export REDIS_HOST="localhost"
export REDIS_PORT="6379"
export JWT_SECRET="your-secret-key"
```

---

## 🏗️ Setup & Run Locally
1️⃣ **Clone the Repository**
```sh
git clone https://github.com/your-username/school-management-system.git
cd school-management-system
```

2️⃣ **Set Up the Database**
- Use MySQL 
- Configure database connection in `application.properties`

3️⃣ **Build & Run the Application**
```sh
mvn clean install
mvn spring-boot:run
```

---

## 🎯 Scheduled Tasks
A **Spring Task Scheduler** runs every **10 minutes** to fetch all users and print details:

```java
@Scheduled(cron = "0 */10 * * * ?") // Runs every 10 mins
public void fetchAllUsers() {
    List<User> users = userRepository.findAll();
    users.forEach(System.out::println);
}
```

---

## ✨ Author
👤 **GAURAV YADAV**  
🔗 [GitHub](https://github.com/gauravyadav6681) | [LinkedIn](https://www.linkedin.com/in/gaurav-yadav-4673971a0/)  

---

## 🚀 Happy Coding! 🎉

