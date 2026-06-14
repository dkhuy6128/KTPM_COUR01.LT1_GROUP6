package com.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduleapp.model.Priority;
import com.scheduleapp.model.Task;
import com.scheduleapp.model.TaskStatus;
import com.scheduleapp.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Tìm tất cả task của một user
    List<Task> findByUser(User user);
    
    // Tìm task theo user và status
    List<Task> findByUserAndStatus(User user, TaskStatus status);
    
    // Tìm task theo user và priority
    List<Task> findByUserAndPriority(User user, Priority priority);
    
    // Tìm task theo user và category
    List<Task> findByUserAndCategory_Id(User user, Long categoryId);
    
    // Tìm task sắp tới
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.startTime BETWEEN :now AND :tomorrow AND t.status != 'COMPLETED'")
    List<Task> findUpcomingTasks(@Param("user") User user, @Param("now") LocalDateTime now, @Param("tomorrow") LocalDateTime tomorrow);
    
    // Tìm task sắp hết hạn
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.endTime < :now AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("user") User user, @Param("now") LocalDateTime now);
    
    // Đếm task theo status
    Long countByUserAndStatus(User user, TaskStatus status);
    
    // Đếm task theo priority
    Long countByUserAndPriority(User user, Priority priority);
    
    // Tìm task theo title
    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String title);
    @Query("SELECT t FROM Task t WHERE t.user = :user AND DATE(t.startTime) = CURRENT_DATE")
    List<Task> findTodayTasks(@Param("user") User user);
}
