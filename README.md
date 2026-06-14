# 📔 Personal Task Manager - Ứng Dụng Quản Lý Công Việc Cá Nhân

> Ứng dụng web giúp người dùng quản lý công việc hằng ngày, lập kế hoạch, theo dõi tiến độ và tối ưu thời gian làm việc bằng Pomodoro Timer. Hệ thống hỗ trợ quản lý công việc, danh mục, nhắc nhở và thống kê năng suất với giao diện trực quan theo từng tài khoản người dùng.

---

## 🔑 Chức Năng Chính

### 👤 Người dùng

* 📝 Đăng ký / đăng nhập tài khoản
* 👤 Cập nhật thông tin cá nhân
* 🗑️ Xóa tài khoản và dữ liệu liên quan

### 📋 Quản lý công việc

* 📝 Thêm / sửa / xoá công việc
* 📅 Quản lý thời gian bắt đầu, kết thúc
* 📌 Theo dõi trạng thái: `TODO`, `IN_PROGRESS`, `COMPLETED`
* 🎨 Phân loại độ ưu tiên: `LOW`, `MEDIUM`, `HIGH`, `URGENT`
* 📂 Tạo và quản lý danh mục công việc

### ⏰ Nhắc nhở & Quản lý thời gian

* 🔔 Thiết lập nhắc nhở cho công việc
* 🍅 Sử dụng Pomodoro Timer (25 phút làm việc, 5 phút nghỉ)
* 📈 Theo dõi số phiên Pomodoro đã hoàn thành

### 📊 Thống kê

* 📋 Tổng số công việc
* ✅ Tỷ lệ hoàn thành
* 🎯 Phân bố theo mức độ ưu tiên
* 🍅 Báo cáo năng suất Pomodoro

---

## ⚙️ Công Nghệ Sử Dụng

| Thành phần | Công nghệ                             |
| ---------- | ------------------------------------- |
| Backend    | Spring Boot 3.5.3                     |
| Frontend   | Thymeleaf + JavaScript + Tailwind CSS |
| Database   | MySQL 8.x                             |
| ORM        | Hibernate                             |
| Build Tool | Maven Wrapper (`./mvnw`)              |
| Java       | Java 17                               |

---

## 📋 Môi Trường Cần Thiết

* Java 17 hoặc cao hơn
* Maven 3.8+ (đã kèm `mvnw`)
* MySQL 8.x
* IDE: IntelliJ IDEA / Eclipse / VS Code
