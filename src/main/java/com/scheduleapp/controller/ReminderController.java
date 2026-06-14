package com.scheduleapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.model.Reminder;
import com.scheduleapp.model.Task;
import com.scheduleapp.service.ReminderService;
import com.scheduleapp.service.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;
    private final TaskService taskService;

    public ReminderController(ReminderService reminderService, TaskService taskService) {
        this.reminderService = reminderService;
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping("/{taskId}")
    public ResponseEntity<?> createReminder(@PathVariable Long taskId, @RequestBody Reminder reminder) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task không tồn tại");
        }

        reminder.setTask(taskOpt.get());
        Reminder createdReminder = reminderService.createReminder(reminder);
        return ResponseEntity.ok(createdReminder);
    }

    // READ - Get all reminders by task
    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getRemindersByTask(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task không tồn tại");
        }

        List<Reminder> reminders = reminderService.getRemindersByTask(taskOpt.get());
        return ResponseEntity.ok(reminders);
    }

    // READ - Get reminder by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable Long id) {
        Optional<Reminder> reminderOpt = reminderService.getReminderById(id);
        if (reminderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reminderOpt.get());
    }

    // READ - Get due reminders
    @GetMapping("/due/list")
    public ResponseEntity<?> getDueReminders() {
        List<Reminder> reminders = reminderService.getDueReminders();
        return ResponseEntity.ok(reminders);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable Long id, @RequestBody Reminder reminderDetails) {
        Optional<Reminder> reminderOpt = reminderService.getReminderById(id);
        if (reminderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reminder reminder = reminderOpt.get();
        reminder.setRemindTime(reminderDetails.getRemindTime());
        reminder.setMessage(reminderDetails.getMessage());

        Reminder updatedReminder = reminderService.updateReminder(reminder);
        return ResponseEntity.ok(updatedReminder);
    }

    // UPDATE - Mark as notified
    @PutMapping("/{id}/notified")
    public ResponseEntity<?> markAsNotified(@PathVariable Long id) {
        reminderService.markAsNotified(id);
        return ResponseEntity.ok("Đã đánh dấu thông báo");
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.ok("Đã xóa reminder");
    }

    // DELETE - Clean old reminders
    @DeleteMapping("/cleanup/old")
    public ResponseEntity<?> cleanOldReminders() {
        reminderService.cleanOldReminders();
        return ResponseEntity.ok("Đã xóa reminder cũ");
    }
}
