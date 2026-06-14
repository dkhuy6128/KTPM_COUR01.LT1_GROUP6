package com.scheduleapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.scheduleapp.model.Reminder;
import com.scheduleapp.model.Task;

public interface ReminderService {
    
    Reminder createReminder(Reminder reminder);
    Optional<Reminder> getReminderById(Long id);
    List<Reminder> getRemindersByTask(Task task);
    Reminder updateReminder(Reminder reminder);
    void deleteReminder(Long id);
    
    // Reminder specific methods
    List<Reminder> getDueReminders();
    void markAsNotified(Long reminderId);
    void cleanOldReminders();
}
