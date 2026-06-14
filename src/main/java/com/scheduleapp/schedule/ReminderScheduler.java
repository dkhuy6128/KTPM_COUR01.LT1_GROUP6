package com.scheduleapp.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scheduleapp.model.Reminder;
import com.scheduleapp.service.ReminderService;

import java.util.List;

@Component
@EnableScheduling
public class ReminderScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);
    private final ReminderService reminderService;

    public ReminderScheduler(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkAndNotifyReminders() {
        try {
            List<Reminder> dueReminders = reminderService.getDueReminders();
            for (Reminder reminder : dueReminders) {
                logger.info("Reminder: {} - {}", reminder.getId(), reminder.getMessage());
                reminderService.markAsNotified(reminder.getId());
            }
        } catch (Exception e) {
            logger.error("Lỗi kiểm tra reminders", e);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanOldReminders() {
        try {
            reminderService.cleanOldReminders();
        } catch (Exception e) {
            logger.error("Lỗi dọn reminders", e);
        }
    }
}
