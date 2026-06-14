package com.scheduleapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.scheduleapp.model.*;

public interface TaskService {
    
    // CRUD
    Task createTask(Task task, User user);
    Optional<Task> getTaskById(Long id);
    List<Task> getAllTasksByUser(User user);
    Task updateTask(Task task);
    void deleteTask(Long id);
    void startTask(Task task);
    
    // Query methods
    List<Task> getTasksByStatus(User user, TaskStatus status);
    List<Task> getTasksByPriority(User user, Priority priority);
    List<Task> getUpcomingTasks(User user);
    List<Task> getOverdueTasks(User user);
    List<Task> searchTasks(User user, String keyword);
    List<Task> getTodayTasks(User user);
    List<Task> getCompletedTasks(User user);
    
    // Statistics
    Long countTasksByStatus(User user, TaskStatus status);
    Long countTasksByPriority(User user, Priority priority);

    // FIX: thêm method để PomodoroServiceImpl có thể gọi
    void addWorkMinutes(Long taskId, int minutes);
}
