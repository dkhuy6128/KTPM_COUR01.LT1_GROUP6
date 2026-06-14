package com.scheduleapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.model.*;
import com.scheduleapp.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task createTask(Task task, User user) {
        task.setUser(user);
        task.setStatus(TaskStatus.TODO);
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByStatus(User user, TaskStatus status) {
        return taskRepository.findByUserAndStatus(user, status);
    }

    @Override
    public List<Task> getTasksByPriority(User user, Priority priority) {
        return taskRepository.findByUserAndPriority(user, priority);
    }

    @Override
    public List<Task> getUpcomingTasks(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        return taskRepository.findUpcomingTasks(user, now, tomorrow);
    }

    @Override
    public List<Task> getOverdueTasks(User user) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findOverdueTasks(user, now);
    }

    @Override
    public List<Task> searchTasks(User user, String keyword) {
        return taskRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

    @Override
    public Long countTasksByStatus(User user, TaskStatus status) {
        return taskRepository.countByUserAndStatus(user, status);
    }

    @Override
    public Long countTasksByPriority(User user, Priority priority) {
        return taskRepository.countByUserAndPriority(user, priority);
    }
    @Override
    @Transactional
    public void startTask(Task task) {

    List<Task> runningTasks =
            taskRepository.findByUserAndStatus(task.getUser(), TaskStatus.IN_PROGRESS);

    for (Task t : runningTasks) {
        t.setStatus(TaskStatus.TODO);
        taskRepository.save(t);
    }

    task.setStatus(TaskStatus.IN_PROGRESS);
    taskRepository.save(task);

    }
    @Override
    public List<Task> getTodayTasks(User user) {
    return taskRepository.findTodayTasks(user);
    }
    @Override
    public List<Task> getCompletedTasks(User user) {
        return taskRepository.findByUserAndStatus(user, TaskStatus.COMPLETED);
    }

    // FIX: implement addWorkMinutes để PomodoroServiceImpl biên dịch được
    @Override
    @Transactional
    public void addWorkMinutes(Long taskId, int minutes) {
        taskRepository.findById(taskId).ifPresent(task -> {
            int current = task.getWorkMinutes() != null ? task.getWorkMinutes() : 0;
            task.setWorkMinutes(current + minutes);
            taskRepository.save(task);
        });
    }
}
