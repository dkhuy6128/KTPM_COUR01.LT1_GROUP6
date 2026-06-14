-- Sample users (password: password123, da ma hoa BCrypt)
INSERT INTO users (id, username, password, full_name, email, phone_number, is_active, created_at, updated_at) VALUES
(1, 'huydk', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Do Khac Huy', 'dkhuy6128@gmail.com', '0912345678', true, 1686733200000, 1686733200000),
(2, 'huydo', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Do Huy', 'huydo@gmail.com', '0987654321', true, 1686733200000, 1686733200000);

-- ==================== SAMPLE CATEGORIES ====================
INSERT INTO categories (id, name, color, description, user_id) VALUES
(1, 'Work', '#3B82F6', 'Công việc văn phòng, dự án', 1),
(2, 'Study', '#10B981', 'Học tập, nghiên cứu công nghệ mới', 1),
(3, 'Personal', '#F59E0B', 'Đời sống cá nhân, gia đình', 1),
(4, 'Work', '#3B82F6', 'Công việc văn phòng, dự án', 2);

-- ==================== SAMPLE TASKS ====================
INSERT INTO tasks (id, title, description, start_time, end_time, priority, status, completed_percentage, user_id, category_id, created_at, updated_at) VALUES
(1, 'Học môn Kiến trúc phần mềm', 'Nghiên cứu Spring Boot và thiết kế cơ sở dữ liệu cho bài tập lớn', '2026-06-14 08:00:00', '2026-06-14 11:00:00', 'HIGH', 'IN_PROGRESS', 50, 1, 2, 1686733200000, 1686733200000),
(2, 'Họp nhóm đồ án tốt nghiệp', 'Thảo luận với các thành viên nhóm về kế hoạch triển khai', '2026-06-15 14:00:00', '2026-06-15 15:30:00', 'URGENT', 'TODO', 0, 1, 1, 1686733200000, 1686733200000),
(3, 'Tập thể dục buổi chiều', 'Chạy bộ 5km quanh công viên gần nhà', '2026-06-14 17:00:00', '2026-06-14 18:00:00', 'LOW', 'COMPLETED', 100, 1, 3, 1686733200000, 1686733200000);

-- ==================== SAMPLE REMINDERS ====================
INSERT INTO reminders (id, task_id, remind_time, message, is_notified, created_at) VALUES
(1, 1, '2026-06-14 07:50:00', 'Chuẩn bị bắt đầu học môn Kiến trúc phần mềm!', false, 1686733200000),
(2, 2, '2026-06-15 13:45:00', 'Sắp tới giờ họp nhóm đồ án, nhớ mang theo laptop!', false, 1686733200000);

-- ==================== SAMPLE POMODORO SESSIONS ====================
INSERT INTO pomodoro_sessions (id, task_id, work_time_minutes, break_time_minutes, total_sessions, completed_sessions, is_active, is_completed, started_at, completed_at, created_at, updated_at) VALUES
(1, 1, 25, 5, 4, 2, false, false, '2026-06-14 08:00:00', NULL, 1686733200000, 1686733200000);

-- ==================== SAMPLE STATISTICS ====================
INSERT INTO statistics (id, user_id, total_tasks, completed_tasks, pending_tasks, cancelled_tasks, urgent_tasks, high_priority_tasks, medium_priority_tasks, low_priority_tasks, total_pomodoro_sessions, completed_pomodoro_sessions, completion_rate, last_updated) VALUES
(1, 1, 3, 1, 2, 0, 1, 1, 0, 1, 4, 2, 33.33, 1686733200000),
(2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0, 1686733200000);
