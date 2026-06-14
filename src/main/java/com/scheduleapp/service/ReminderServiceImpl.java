package com.scheduleapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.model.Reminder;
import com.scheduleapp.model.Task;
import com.scheduleapp.repository.ReminderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderServiceImpl(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    @Transactional
    public Reminder createReminder(Reminder reminder) {
        if (reminder.getMessage() == null) {
            reminder.setMessage("Bạn có việc sắp tới: " + reminder.getTask().getTitle());
        }
        return reminderRepository.save(reminder);
    }

    @Override
    public Optional<Reminder> getReminderById(Long id) {
        return reminderRepository.findById(id);
    }

    @Override
    public List<Reminder> getRemindersByTask(Task task) {
        return reminderRepository.findByTask(task);
    }

    @Override
    @Transactional
    public Reminder updateReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    @Override
    @Transactional
    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }

    @Override
    public List<Reminder> getDueReminders() {
        LocalDateTime now = LocalDateTime.now();
        return reminderRepository.findDueReminders(now);
    }

    @Override
    @Transactional
    public void markAsNotified(Long reminderId) {
        Optional<Reminder> reminder = reminderRepository.findById(reminderId);
        if (reminder.isPresent()) {
            Reminder r = reminder.get();
            r.setIsNotified(true);
            reminderRepository.save(r);
        }
    }

    @Override
    @Transactional
    public void cleanOldReminders() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        reminderRepository.deleteByRemindTimeBefore(oneWeekAgo);
    }
}
