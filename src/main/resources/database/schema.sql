-- ==================== USERS TABLE ====================
CREATE TABLE IF NOT EXISTS users (
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

-- ==================== CATEGORIES TABLE ====================
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(10),
    description TEXT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_category (user_id, name)
);

-- ==================== TASKS TABLE ====================
CREATE TABLE IF NOT EXISTS tasks (
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
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    INDEX idx_user_status (user_id, status),
    INDEX idx_user_priority (user_id, priority),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time)
);

-- ==================== REMINDERS TABLE ====================
CREATE TABLE IF NOT EXISTS reminders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    remind_time DATETIME NOT NULL,
    message TEXT,
    is_notified BOOLEAN DEFAULT false,
    created_at BIGINT,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    INDEX idx_remind_time (remind_time),
    INDEX idx_is_notified (is_notified)
);

-- ==================== POMODORO_SESSIONS TABLE ====================
CREATE TABLE IF NOT EXISTS pomodoro_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    work_time_minutes INT DEFAULT 25,
    break_time_minutes INT DEFAULT 5,
    total_sessions INT DEFAULT 1,
    completed_sessions INT DEFAULT 0,
    is_active BOOLEAN DEFAULT false,
    is_completed BOOLEAN DEFAULT false,
    started_at DATETIME,
    completed_at DATETIME,
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    INDEX idx_task_active (task_id, is_active)
);

-- ==================== STATISTICS TABLE ====================
CREATE TABLE IF NOT EXISTS statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_tasks INT DEFAULT 0,
    completed_tasks INT DEFAULT 0,
    pending_tasks INT DEFAULT 0,
    cancelled_tasks INT DEFAULT 0,
    urgent_tasks INT DEFAULT 0,
    high_priority_tasks INT DEFAULT 0,
    medium_priority_tasks INT DEFAULT 0,
    low_priority_tasks INT DEFAULT 0,
    total_pomodoro_sessions INT DEFAULT 0,
    completed_pomodoro_sessions INT DEFAULT 0,
    completion_rate DOUBLE DEFAULT 0.0,
    last_updated BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

