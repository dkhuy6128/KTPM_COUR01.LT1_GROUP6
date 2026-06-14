# 🚀 Quick Start Guide - Personal Task Manager

## 1️⃣ Chuẩn Bị Môi Trường

### Kiểm Tra Java
```bash
java -version  # Phải là Java 17+
```

### Kiểm Tra Maven
```bash
mvn -version   # Phải là Maven 3.8+
```

### Kiểm Tra MySQL
```bash
mysql --version  # Phải là MySQL 8.x+
```

---

## 2️⃣ Tạo Database

### A. Tạo Database trong MySQL
```bash
# Mở MySQL Command Line
mysql -u root -p

# Nhập password (mặc định: 1111)

# Tạo database
CREATE DATABASE personal_task_db;
CREATE USER 'root'@'localhost' IDENTIFIED BY '1111';
GRANT ALL PRIVILEGES ON personal_task_db.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### B. Hoặc Tạo Bằng Script
```bash
mysql -u root -p < src/main/resources/database/schema.sql
```

---

## 3️⃣ Cấu Hình Ứng Dụng

### Sửa `application.properties`
```properties
# File: src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/personal_task_db
spring.datasource.username=root
spring.datasource.password=1111    # Sửa password nếu cần

spring.jpa.hibernate.ddl-auto=update
```

---

## 4️⃣ Chạy Ứng Dụng

### Cách 1: Maven Command Line
```bash
# Build project
mvn clean install

# Chạy ứng dụng
./mvnw spring-boot:run

# Hoặc trên Windows
mvnw spring-boot:run
```

### Cách 2: IDE (IntelliJ IDEA)
1. Mở project
2. Right-click `DiaryappApplication.java`
3. Chọn "Run DiaryappApplication"

### Cách 3: JAR File
```bash
# Build JAR
mvn clean package

# Chạy JAR
java -jar target/diaryapp-0.0.1-SNAPSHOT.jar
```

---

## 5️⃣ Truy Cập Ứng Dụng

Khi ứng dụng chạy, bạn sẽ thấy:
```
Started DiaryappApplication in X.XXX seconds
```

### Frontend Pages
- **Login**: http://localhost:8080/login
- **Register**: http://localhost:8080/register
- **Dashboard**: http://localhost:8080/dashboard
- **Pomodoro**: http://localhost:8080/pomodoro

### API Documentation
- **Base URL**: http://localhost:8080/api

---

## 6️⃣ Test APIs (Postman/cURL)

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "confirmPassword": "password123",
    "fullName": "Test User",
    "email": "test@example.com"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Create Task (sau khi lấy userId)
```bash
curl -X POST http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Master Spring Boot framework",
    "startTime": "2025-06-14T08:00:00",
    "endTime": "2025-06-14T10:00:00",
    "priority": "HIGH"
  }'
```

### Get All Tasks
```bash
curl -X GET http://localhost:8080/api/tasks/1
```

---

## 7️⃣ Troubleshooting

### Port đã được sử dụng
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

### Database Connection Error
- Kiểm tra MySQL đang chạy
- Kiểm tra username/password trong `application.properties`
- Kiểm tra database `personal_task_db` đã được tạo

### Build Error
```bash
# Clean cache
mvn clean

# Rebuild
mvn package -DskipTests
```

---

## 8️⃣ Folder Structure

```
project/
├── src/
│   ├── main/
│   │   ├── java/com/diaryapp/
│   │   │   ├── model/          (Entities)
│   │   │   ├── repository/      (Database Access)
│   │   │   ├── service/         (Business Logic)
│   │   │   ├── controller/      (API Endpoints)
│   │   │   ├── dto/             (Data Transfer Objects)
│   │   │   ├── config/          (Configuration)
│   │   │   ├── util/            (Utilities)
│   │   │   ├── schedule/        (Schedulers)
│   │   │   └── exception/       (Exception Handlers)
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── database/
│   │   │   │   ├── schema.sql
│   │   │   │   └── data.sql
│   │   │   └── templates/       (HTML Pages)
│   │   └── DiaryappApplication.java
│   └── test/                     (Test Files)
├── pom.xml                       (Maven Configuration)
└── README.md                     (Documentation)
```

---

## 9️⃣ Development Tools

### Database GUI
- **MySQL Workbench**: Quản lý database
- **DBeaver**: Quản lý database
- **phpMyAdmin**: Web-based management

### API Testing
- **Postman**: GUI API tester
- **Insomnia**: API tester
- **cURL**: Command line tool

### IDE
- **IntelliJ IDEA**: Full IDE
- **VS Code**: Lightweight + Extensions
- **Eclipse**: Free IDE

---

## 🔟 Next Steps

1. ✅ Chạy ứng dụng
2. ✅ Đăng ký tài khoản
3. ✅ Tạo categories
4. ✅ Tạo tasks
5. ✅ Tạo reminders
6. ✅ Test Pomodoro Timer
7. ✅ Xem Statistics
8. 📚 Tìm hiểu code
9. 💡 Customize features
10. 🚀 Deploy lên production

---

## 📞 Support

Nếu gặp vấn đề:
1. Kiểm tra console logs
2. Kiểm tra application.properties
3. Kiểm tra database connection
4. Xem README.md
5. Contact: dkhuy6128@gmail.com

---

**Happy Coding! 🎉**
