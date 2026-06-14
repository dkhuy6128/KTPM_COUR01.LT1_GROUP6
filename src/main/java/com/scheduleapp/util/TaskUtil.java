package com.scheduleapp.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.scheduleapp.model.Priority;
import com.scheduleapp.model.Task;
import com.scheduleapp.model.TaskStatus;

/**
 * Utility class cho Task operations
 */
public class TaskUtil {

    /**
     * Tính thời gian còn lại cho task (tính bằng phút)
     */
    public static Long getRemainingMinutes(Task task) {
        LocalDateTime now = LocalDateTime.now();
        if (task.getEndTime().isBefore(now)) {
            return 0L;
        }
        return ChronoUnit.MINUTES.between(now, task.getEndTime());
    }

    /**
     * Kiểm tra task có sắp tới không (trong 24 giờ)
     */
    public static boolean isUpcoming(Task task) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        return task.getStartTime().isAfter(now) && task.getStartTime().isBefore(tomorrow);
    }

    /**
     * Kiểm tra task có quá hạn không
     */
    public static boolean isOverdue(Task task) {
        LocalDateTime now = LocalDateTime.now();
        return task.getEndTime().isBefore(now) && !task.getStatus().equals(TaskStatus.COMPLETED);
    }

    /**
     * Lấy màu theo priority
     */
    public static String getPriorityColor(Priority priority) {
        return switch (priority) {
            case LOW -> "#4CAF50";      // Green
            case MEDIUM -> "#FF9800";   // Orange
            case HIGH -> "#F44336";     // Red
            case URGENT -> "#9C27B0";   // Purple
        };
    }

    /**
     * Lấy icon theo priority
     */
    public static String getPriorityIcon(Priority priority) {
        return switch (priority) {
            case LOW -> "🟢";
            case MEDIUM -> "🟡";
            case HIGH -> "🟠";
            case URGENT -> "🔴";
        };
    }

    /**
     * Lấy icon theo status
     */
    public static String getStatusIcon(TaskStatus status) {
        return switch (status) {
            case TODO -> "📝";
            case IN_PROGRESS -> "⏳";
            case COMPLETED -> "✅";
            case CANCELLED -> "❌";
        };
    }
}
