# � Personal Task Manager - Ứng Dụng Quản Lý Lịch Làm Việc Cá Nhân

> Ứng dụng web hiện đại để quản lý công việc hàng ngày, lên kế hoạch, theo dõi tiến độ, và tối ưu hóa thời gian làm việc với Pomodoro Timer.

---

## 🎯 Các Tính Năng Chính

### ✨ 1. Quản Lý Người Dùng (User Management)
- 📝 **Đăng ký** - Tạo tài khoản mới với thông tin cá nhân
- 🔐 **Đăng nhập** - Xác thực an toàn với username/password
- 👤 **Cập nhật hồ sơ** - Chỉnh sửa thông tin cá nhân (email, số điện thoại)
- 🗑️ **Xóa tài khoản** - Xóa tài khoản và toàn bộ dữ liệu

### 📋 2. CRUD Task (Tạo, Đọc, Cập Nhật, Xóa)
- ➕ **Tạo Task** - Thêm công việc mới với tiêu đề, mô tả, thời gian
- 📄 **Xem Task** - Xem danh sách toàn bộ tasks hoặc task riêng lẻ
- ✏️ **Cập Nhật Task** - Sửa thông tin, trạng thái, độ ưu tiên
- 🗑️ **Xóa Task** - Xóa task không còn cần thiết

**Ví dụ tạo task:**
```json
{
    "title": "Học KTPM",
    "description": "Học Spring Boot + Database",
    "startTime": "2025-06-14T08:00:00",
    "endTime": "2025-06-14T10:00:00",
    "priority": "HIGH"
}
```

### 🎨 3. Phân Loại Độ Ưu Tiên (Priority System)
Các mức độ ưu tiên với màu sắc riêng:
- 🟢 **LOW** (Thấp) - Màu xanh lá
- 🟡 **MEDIUM** (Trung bình) - Màu vàng
- 🟠 **HIGH** (Cao) - Màu cam
- 🔴 **URGENT** (Cấp bách) - Màu đỏ

### 📂 4. Phân Loại (Categories)
- 📑 Tạo các danh mục custom (Work, Personal, Study, ...)
- 🎨 Chọn màu sắc riêng cho mỗi category
- 🔗 Liên kết task với category

### ⏰ 5. Hệ Thống Nhắc Nhở (Reminders)
- 🔔 Tạo reminder cho mỗi task
- ⏲️ Đặt thời gian nhắc nhở trước khi task bắt đầu
- 📬 Hệ thống tự động kiểm tra và gửi thông báo mỗi phút
- ✅ Đánh dấu reminder đã thông báo

### 🍅 6. Pomodoro Timer
Kỹ thuật Pomodoro giúp tối ưu hóa thời gian làm việc:
- ⏱️ **25 phút** làm việc tập trung
- ☕ **5 phút** nghỉ ngơi
- 🔁 Lặp lại 4 lần, sau đó nghỉ **15 phút**
- 📊 Theo dõi số session hoàn thành cho mỗi task

**Công thức:**
```
1 Pomodoro = 25 phút làm việc + 5 phút nghỉ
4 Pomodoro = 1 chu kỳ dài (115 phút + 15 phút break)
```

### 📊 7. Thống Kê & Phân Tích (Statistics)
- 📈 **Tổng Tasks**: Tổng số tasks trong hệ thống
- ✅ **Hoàn Thành**: Số tasks đã hoàn thành
- ⏳ **Đang Làm**: Số tasks chưa hoàn thành
- 📉 **Tỷ Lệ Hoàn Thành**: Phần trăm công việc đã xong
- 🎯 **Phân Bố Độ Ưu Tiên**: Số tasks theo từng mức độ ưu tiên
- 🍅 **Pomodoro Statistics**: Tổng số session Pomodoro

**Công thức tính Completion Rate:**
```
Completion Rate = (Completed Tasks / Total Tasks) × 100%
```

---

## ⚙️ Công Nghệ Sử Dụng

| Thành phần | Công Nghệ | Phiên Bản |
|-----------|-----------|----------|
| **Backend** | Spring Boot | 3.5.3 |
| **Framework** | Spring Data JPA + Spring Web | 3.5.3 |
| **Frontend** | Thymeleaf + Vanilla JavaScript | - |
| **Database** | MySQL | 8.x |
| **Build Tool** | Maven | 3.8+ |
| **Java** | Java | 17+ |
| **ORM** | Hibernate | - |

---

## 📋 Cấu Trúc Database

### 📊 Các Bảng Chính

#### 1. **users** - Lưu thông tin người dùng
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    is_active BOOLEAN DEFAULT true,
    created_at BIGINT,
    updated_at BIGINT
);
```

#### 2. **tasks** - Lưu các công việc
```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    priority VARCHAR(20) DEFAULT 'MEDIUM',
    status VARCHAR(20) DEFAULT 'TODO',
    completed_percentage INT DEFAULT 0,
    user_id BIGINT NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

#### 3. **reminders** - Lưu các nhắc nhở
```sql
CREATE TABLE reminders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    remind_time DATETIME NOT NULL,
    message TEXT,
    is_notified BOOLEAN DEFAULT false,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);
```

#### 4. **pomodoro_sessions** - Lưu các session Pomodoro
```sql
CREATE TABLE pomodoro_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    work_time_minutes INT DEFAULT 25,
    break_time_minutes INT DEFAULT 5,
    completed_sessions INT DEFAULT 0,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);
```

#### 5. **statistics** - Lưu thống kê người dùng
```sql
CREATE TABLE statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_tasks INT DEFAULT 0,
    completed_tasks INT DEFAULT 0,
    completion_rate DOUBLE DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

## 🚀 Hướng Dẫn Cài Đặt

### 1️⃣ Yêu Cầu Hệ Thống
- Java 17 hoặc cao hơn
- MySQL 8.x
- Maven 3.8+
- IDE: IntelliJ IDEA / Eclipse / VS Code

### 2️⃣ Clone & Setup

```bash
# Clone dự án
git clone https://github.com/dkhuy6128/OOP_N03_Term3_2025_K17_Group9
cd OOP_N03_Term3_2025_K17_Group9-main

# Cập nhật configuration
# Sửa file: src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/diarydb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3️⃣ Chạy Ứng Dụng

**Cách 1: Maven**
```bash
./mvnw spring-boot:run
```

**Cách 2: IDE**
- Mở project trong IDE
- Click Run → Run DiaryappApplication

**Cách 3: Build & Run**
```bash
./mvnw clean install
java -jar target/diaryapp-0.0.1-SNAPSHOT.jar
```

### 4️⃣ Truy Cập Ứng Dụng
- **Frontend**: http://localhost:8080/login
- **API Documentation**: http://localhost:8080/api/

---

## 📡 API Endpoints

### 🔐 Authentication (Xác Thực)

#### Đăng Ký
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123",
    "confirmPassword": "password123",
    "fullName": "John Doe",
    "email": "john@example.com"
}
```

**Response (Success):**
```json
{
    "userId": 1,
    "username": "johndoe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "message": "Đăng ký thành công",
    "success": true
}
```

#### Đăng Nhập
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123"
}
```

**Response (Success):**
```json
{
    "userId": 1,
    "username": "johndoe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "message": "Đăng nhập thành công",
    "success": true
}
```

---

### 📋 Task Management (Quản Lý Task)

#### Tạo Task
```http
POST /api/tasks/{userId}
Content-Type: application/json

{
    "title": "Học KTPM",
    "description": "Học Spring Boot",
    "startTime": "2025-06-14T08:00:00",
    "endTime": "2025-06-14T10:00:00",
    "priority": "HIGH"
}
```

#### Lấy Tất Cả Tasks
```http
GET /api/tasks/{userId}
```

#### Lấy Tasks theo Status
```http
GET /api/tasks/{userId}/status/TODO
GET /api/tasks/{userId}/status/IN_PROGRESS
GET /api/tasks/{userId}/status/COMPLETED
```

#### Lấy Tasks theo Priority
```http
GET /api/tasks/{userId}/priority/URGENT
GET /api/tasks/{userId}/priority/HIGH
GET /api/tasks/{userId}/priority/MEDIUM
GET /api/tasks/{userId}/priority/LOW
```

#### Lấy Tasks Sắp Tới (24h)
```http
GET /api/tasks/{userId}/upcoming
```

#### Lấy Tasks Quá Hạn
```http
GET /api/tasks/{userId}/overdue
```

#### Tìm Kiếm Tasks
```http
GET /api/tasks/{userId}/search?keyword=KTPM
```

#### Cập Nhật Task
```http
PUT /api/tasks/{taskId}
Content-Type: application/json

{
    "title": "Học KTPM - Updated",
    "status": "IN_PROGRESS",
    "completedPercentage": 50
}
```

#### Xóa Task
```http
DELETE /api/tasks/{taskId}
```

---

### 📂 Categories (Phân Loại)

#### Tạo Category
```http
POST /api/categories/{userId}
Content-Type: application/json

{
    "name": "Work",
    "color": "#3B82F6"
}
```

#### Lấy Tất Cả Categories
```http
GET /api/categories/{userId}
```

#### Cập Nhật Category
```http
PUT /api/categories/{categoryId}
Content-Type: application/json

{
    "name": "Work Updated",
    "color": "#FF5733"
}
```

#### Xóa Category
```http
DELETE /api/categories/{categoryId}
```

---

### ⏰ Reminders (Nhắc Nhở)

#### Tạo Reminder
```http
POST /api/reminders/{taskId}
Content-Type: application/json

{
    "remindTime": "2025-06-14T07:50:00",
    "message": "Sắp bắt đầu học KTPM!"
}
```

#### Lấy Reminders của Task
```http
GET /api/reminders/task/{taskId}
```

#### Lấy Reminders Sắp Tới
```http
GET /api/reminders/due/list
```

#### Đánh Dấu Reminder đã Thông Báo
```http
PUT /api/reminders/{reminderId}/notified
```

#### Xóa Reminder
```http
DELETE /api/reminders/{reminderId}
```

---

### 🍅 Pomodoro Timer

#### Bắt Đầu Session
```http
POST /api/pomodoro/{taskId}/start
```

**Response:**
```json
{
    "id": 1,
    "taskId": 1,
    "workTimeMinutes": 25,
    "breakTimeMinutes": 5,
    "totalSessions": 1,
    "completedSessions": 0,
    "isActive": true
}
```

#### Lấy Active Session
```http
GET /api/pomodoro/task/{taskId}/active
```

#### Hoàn Thành Session
```http
PUT /api/pomodoro/{sessionId}/complete
```

#### Lấy Tất Cả Sessions của Task
```http
GET /api/pomodoro/task/{taskId}
```

---

### 📊 Statistics (Thống Kê)

#### Lấy Statistics của User
```http
GET /api/statistics/{userId}
```

**Response:**
```json
{
    "id": 1,
    "userId": 1,
    "totalTasks": 10,
    "completedTasks": 6,
    "pendingTasks": 3,
    "cancelledTasks": 1,
    "urgentTasks": 2,
    "highPriorityTasks": 3,
    "mediumPriorityTasks": 3,
    "lowPriorityTasks": 2,
    "completionRate": 60.0
}
```

#### Tính Toán Lại Statistics
```http
PUT /api/statistics/{userId}/recalculate
```

#### Lấy Completion Rate
```http
GET /api/statistics/{userId}/completion-rate
```

---

## 🏗️ Cấu Trúc Project

```
src/main/java/com/diaryapp/
├── model/                  # Các model (Entity)
│   ├── User.java
│   ├── Task.java
│   ├── Category.java
│   ├── Reminder.java
│   ├── PomodoroSession.java
│   ├── Statistics.java
│   ├── Priority.java
│   └── TaskStatus.java
├── repository/             # Repositories (Database Layer)
│   ├── UserRepository.java
│   ├── TaskRepository.java
│   ├── CategoryRepository.java
│   ├── ReminderRepository.java
│   ├── PomodoroSessionRepository.java
│   └── StatisticsRepository.java
├── service/                # Services (Business Logic)
│   ├── TaskService.java & TaskServiceImpl.java
│   ├── CategoryService.java & CategoryServiceImpl.java
│   ├── ReminderService.java & ReminderServiceImpl.java
│   ├── PomodoroService.java & PomodoroServiceImpl.java
│   ├── StatisticsService.java & StatisticsServiceImpl.java
│   └── UserService.java & UserServiceImpl.java
├── controller/             # Controllers (API Endpoints)
│   ├── UserController.java
│   ├── TaskController.java
│   ├── CategoryController.java
│   ├── ReminderController.java
│   ├── PomodoroController.java
│   └── StatisticsController.java
├── dto/                    # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── AuthResponse.java
│   └── TaskRequest.java
├── util/                   # Utility Classes
│   └── TaskUtil.java
├── schedule/               # Schedulers
│   └── ReminderScheduler.java
└── DiaryappApplication.java # Main Application Class

src/main/resources/
├── application.properties  # Configuration
├── database/
│   ├── schema.sql         # Database Schema
│   └── data.sql           # Sample Data
└── templates/             # Thymeleaf Templates
    ├── login.html
    ├── register.html
    ├── dashboard.html
    └── pomodoro.html
```

---

## 🎓 Các Kiến Thức KTPM Được Áp Dụng

### 1. **MVC Architecture**
- **Model**: Các entity (User, Task, Category, ...)
- **View**: Thymeleaf templates (login, dashboard, pomodoro)
- **Controller**: REST controllers xử lý requests

### 2. **Design Patterns**
- **Service Pattern**: TaskService, CategoryService, ...
- **Repository Pattern**: JpaRepository cho database access
- **DTO Pattern**: RegisterRequest, AuthResponse, ...
- **Scheduler Pattern**: ReminderScheduler cho cron jobs

### 3. **SOLID Principles**
- **S**ingle Responsibility: Mỗi class có 1 trách nhiệm
- **O**pen/Closed: Mở rộng bằng interface, không sửa implementation
- **L**iskov Substitution: Services implement interfaces
- **I**nterface Segregation: Tách interface nhỏ (TaskService, ReminderService)
- **D**ependency Inversion: Inject dependencies qua constructor

### 4. **Database Design**
- **Normalization**: Tránh duplicate data, tách bảng hợp lý
- **Foreign Keys**: Liên kết giữa bảng (user → tasks → reminders)
- **Indexes**: Tối ưu truy vấn (user_id, start_time, ...)
- **Transactions**: @Transactional cho consistency

### 5. **Spring Boot Features**
- **Spring Data JPA**: ORM mapping
- **Spring Web**: REST API
- **Thymeleaf**: Template engine
- **Scheduling**: @Scheduled cho reminder checker
- **Validation**: Input validation
- **Exception Handling**: Global error handling

### 6. **Clean Code Practices**
- Naming conventions (camelCase, PascalCase)
- Comments & Javadoc
- Modular functions
- No code duplication
- Error handling

---

## 📚 Hướng Dẫn Sử Dụng

### Flow Cơ Bản:

1. **Đăng Ký → Đăng Nhập** → Nhận userId
2. **Tạo Categories** (nếu cần phân loại)
3. **Tạo Tasks** với title, thời gian, độ ưu tiên
4. **Tạo Reminders** để nhắc nhở
5. **Bắt Đầu Pomodoro** khi làm việc
6. **Xem Statistics** để theo dõi tiến độ

### Example Workflow:

```java
// 1. Register & Login
POST /api/auth/register
→ userId: 1

// 2. Create Category
POST /api/categories/1
{
    "name": "Work",
    "color": "#3B82F6"
}
→ categoryId: 1

// 3. Create Task
POST /api/tasks/1
{
    "title": "Complete KTPM Project",
    "startTime": "2025-06-14T08:00:00",
    "endTime": "2025-06-14T12:00:00",
    "priority": "HIGH",
    "categoryId": 1
}
→ taskId: 1

// 4. Create Reminder
POST /api/reminders/1
{
    "remindTime": "2025-06-14T07:55:00",
    "message": "KTPM project starts in 5 min!"
}

// 5. Start Pomodoro
POST /api/pomodoro/1/start

// 6. View Statistics
GET /api/statistics/1
→ Completion Rate: 60%, Total Tasks: 10, ...
```

---

## 🤝 Contribution & Support

Nếu có vấn đề hoặc suggestions, vui lòng:
1. Mở issue trên GitHub
2. Submit pull request
3. Liên hệ: dkhuy6128@gmail.com

---

## 📄 License

Project này được cấp phép dưới **MIT License**.

---

**Phát triển bởi:** K17 Group 9 - OOP Class  
**Ngôn Ngữ:** Java 17 + Spring Boot 3.5.3  
**Thời Gian Phát Triển:** Jun 2025

**Happy Coding! 🚀**


Hoặc tải về bằng nút "Download ZIP" và giải nén.

---

### 2️⃣ Tạo cơ sở dữ liệu MySQL

Đăng nhập MySQL rồi tạo database:

```sql
CREATE DATABASE IF NOT EXISTS diarydb;
```

Sau đó chạy 2 file SQL có sẵn:

```sql
source src/main/resources/database/schema.sql;
source src/main/resources/database/data.sql;
```
### ⚠️ Nếu gặp lỗi `Failed to open file` khi dùng `source`

> 📌 Lỗi thường xảy ra khi **đường dẫn file sai** hoặc **MySQL đang ở thư mục khác**

**💡 Cách xử lý:**

1. **Tìm đường dẫn đầy đủ** đến file `schema.sql` và `data.sql` trên máy bạn.  
   Ví dụ: C:/Users/YourName/Downloads/diaryapp/src/main/resources/database/schema.sql

2. **Dán đường dẫn vào lệnh `source` trong MySQL terminal:**
```sql
source C:/Users/YourName/Downloads/diaryapp/src/main/resources/database/schema.sql;
source C:/Users/YourName/Downloads/diaryapp/src/main/resources/database/data.sql;
```

> ✅ Bạn có thể chạy bằng MySQL Workbench hoặc terminal đều được.

---

### 3️⃣ Cấu hình kết nối cơ sở dữ liệu

Mở file:

#### `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/personal_task_db
spring.datasource.username=root
spring.datasource.password=your_password
```

> 🔐 Thay `your_password` bằng mật khẩu MySQL thật trên máy bạn.

---

### 4️⃣ Chạy ứng dụng

#### 👉 Cách 1: Qua IntelliJ IDEA (giao diện)
- Mở project
- Chạy file `DiaryappApplication.java`
- Truy cập [http://localhost:8080](http://localhost:8080)

#### 👉 Cách 2: Qua terminal
```bash
./mvnw spring-boot:run
```

---

## 👨‍💻 Phân Công Thành Viên

### 1. **Đỗ Khắc Huy**
- 🔧 Back-end (Service, Controller, xử lý logic nghiệp vụ)  
- 🧪 Kiểm thử và fix bug Back-end  
- 🛠️ Thiết kế cơ sở dữ liệu (bảng `users`, `categories`, `tasks`, `reminders`, `pomodoro_sessions`, `statistics`)  
- 🧮 Viết API quản lý người dùng và công việc cá nhân  
- ⚙️ Cấu hình `application.properties` và khởi tạo DB từ `schema.sql`, `data.sql`

### 2. **Đỗ Huy**
- 🌐 Front-end (Thymeleaf + Tailwind CSS)  
- 🖼️ Thiết kế giao diện quản lý task, dashboard, danh sách, thống kê  
- 💅 CSS layout, responsive cho toàn bộ UI  
- 🧪 Kiểm thử UI & logic kết nối Front–Back  
- 🎨 Thiết kế giao diện Pomodoro & Reminders

### 3. **Khắc Huy Đỗ**
- 📝 Viết tài liệu `README.md`, hướng dẫn cài đặt & triển khai  
- 🧰 Xử lý cấu hình Maven (`pom.xml`, `./mvnw`)  
- 🗂️ Quản lý thư mục tài nguyên (`resources/`)  
- 🧪 Kiểm tra toàn bộ flow app (từ nhập → lưu → thống kê)  
- 🔗 Thử nghiệm tương thích với MySQL và IDE khác nhau

---

## 📌 Ghi Chú

- Ứng dụng sử dụng `schema.sql` + `data.sql` nên **cần đảm bảo MySQL đang chạy trước khi khởi động app**
- Nếu không muốn load dữ liệu mẫu, có thể để trống file `data.sql`
- Mật khẩu MySQL nên đặt đúng và dùng quyền đủ để tạo bảng

---

## 📬 Liên Hệ

- Email: [23017163@st.phenikaa-uni.edu.vn@gmail.com](mailto:23017163@st.phenikaa-uni.edu.vn)  
- GitHub: [github.com/dkhuy6128](https://github.com/dkhuy6128)

---
