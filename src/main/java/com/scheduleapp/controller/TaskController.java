package com.scheduleapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.model.*;
import com.scheduleapp.service.StatisticsService;
import com.scheduleapp.service.TaskService;
import com.scheduleapp.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final StatisticsService statisticsService;

    public TaskController(TaskService taskService, UserService userService, StatisticsService statisticsService) {
        this.taskService = taskService;
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    // CREATE
    @PostMapping("/{userId}")
    public ResponseEntity<?> createTask(@PathVariable Long userId, @RequestBody Task task) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        Task createdTask = taskService.createTask(task, userOpt.get());
        statisticsService.recalculateStatistics(userOpt.get());
        return ResponseEntity.ok(createdTask);
    }

    // READ - Get all tasks
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllTasks(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.getAllTasksByUser(userOpt.get());
        return ResponseEntity.ok(tasks);
    }

    // READ - Get task by ID
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskOpt.get());
    }

    // READ - Get tasks by status
    @GetMapping("/{userId}/status/{status}")
    public ResponseEntity<?> getTasksByStatus(@PathVariable Long userId, @PathVariable TaskStatus status) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.getTasksByStatus(userOpt.get(), status);
        return ResponseEntity.ok(tasks);
    }

    // READ - Get tasks by priority
    @GetMapping("/{userId}/priority/{priority}")
    public ResponseEntity<?> getTasksByPriority(@PathVariable Long userId, @PathVariable Priority priority) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.getTasksByPriority(userOpt.get(), priority);
        return ResponseEntity.ok(tasks);
    }

    // READ - Get upcoming tasks
    @GetMapping("/{userId}/upcoming")
    public ResponseEntity<?> getUpcomingTasks(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.getUpcomingTasks(userOpt.get());
        return ResponseEntity.ok(tasks);
    }

    // READ - Get overdue tasks
    @GetMapping("/{userId}/overdue")
    public ResponseEntity<?> getOverdueTasks(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.getOverdueTasks(userOpt.get());
        return ResponseEntity.ok(tasks);
    }

    // READ - Search tasks
    @GetMapping("/{userId}/search")
    public ResponseEntity<?> searchTasks(@PathVariable Long userId, @RequestParam String keyword) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Task> tasks = taskService.searchTasks(userOpt.get(), keyword);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<?> startTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskService.startTask(task);
        return ResponseEntity.ok("Task started");
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedPercentage(100);
        taskService.updateTask(task);
        statisticsService.recalculateStatistics(task.getUser());
        return ResponseEntity.ok("Task completed");
    }

    // FIX: Thêm endpoint này để Kanban drag & drop hoạt động
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        String statusStr = body.get("status");
        if (statusStr == null) {
            return ResponseEntity.badRequest().body("Thiếu trường 'status'");
        }
        try {
            task.setStatus(TaskStatus.valueOf(statusStr));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status không hợp lệ: " + statusStr);
        }
        taskService.updateTask(task);
        statisticsService.recalculateStatistics(task.getUser());
        return ResponseEntity.ok("Status updated");
    }

    @GetMapping("/{userId}/today")
    public ResponseEntity<?> getTodayTasks(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(taskService.getTodayTasks(user));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<?> getHistory(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(taskService.getCompletedTasks(user));
    }

    @PostMapping("/{taskId}/pomodoro/start")
    public ResponseEntity<?> startPomodoro(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Pomodoro started for task " + taskId);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOpt.get();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStartTime(taskDetails.getStartTime());
        task.setEndTime(taskDetails.getEndTime());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());
        task.setCategory(taskDetails.getCategory());
        task.setCompletedPercentage(taskDetails.getCompletedPercentage());

        Task updatedTask = taskService.updateTask(task);
        statisticsService.recalculateStatistics(task.getUser());
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = taskOpt.get().getUser();
        taskService.deleteTask(id);
        statisticsService.recalculateStatistics(user);
        return ResponseEntity.ok("Đã xóa task");
    }
}
