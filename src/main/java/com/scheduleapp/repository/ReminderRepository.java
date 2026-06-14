package com.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduleapp.model.Reminder;
import com.scheduleapp.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    
    // Tìm tất cả reminder của task
    List<Reminder> findByTask(Task task);
    
    // Tìm reminder chưa được thông báo
    List<Reminder> findByIsNotifiedFalse();
    
    // Tìm reminder sắp tới (chưa thông báo và trong khoảng thời gian sắp tới)
    @Query("SELECT r FROM Reminder r WHERE r.isNotified = false AND r.remindTime <= :now")
    List<Reminder> findDueReminders(@Param("now") LocalDateTime now);
    
    // Xóa reminder đã cũ
    Long deleteByRemindTimeBefore(LocalDateTime dateTime);
}
