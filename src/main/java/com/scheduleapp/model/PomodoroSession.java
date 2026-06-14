package com.scheduleapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pomodoro_sessions")
public class PomodoroSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private Integer workTimeMinutes = 25;  // Thời gian làm việc mặc định
    private Integer breakTimeMinutes = 5;  // Thời gian nghỉ mặc định

    private Integer totalSessions = 0;     // Tổng số session hoàn thành
    private Integer completedSessions = 0; // Số session đã hoàn thành

    private Boolean isActive = false;
    private Boolean isCompleted = false;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private Long createdAt;
    private Long updatedAt;

    public PomodoroSession() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public PomodoroSession(Task task, Integer workTime, Integer breakTime, Integer totalSessions) {
        this();
        this.task = task;
        this.workTimeMinutes = workTime;
        this.breakTimeMinutes = breakTime;
        this.totalSessions = totalSessions;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getWorkTimeMinutes() {
        return workTimeMinutes;
    }

    public void setWorkTimeMinutes(Integer workTimeMinutes) {
        this.workTimeMinutes = workTimeMinutes;
    }

    public Integer getBreakTimeMinutes() {
        return breakTimeMinutes;
    }

    public void setBreakTimeMinutes(Integer breakTimeMinutes) {
        this.breakTimeMinutes = breakTimeMinutes;
    }

    public Integer getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Integer totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Integer getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Integer completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "PomodoroSession{" +
                "id=" + id +
                ", workTime=" + workTimeMinutes + "min" +
                ", completed=" + completedSessions + "/" + totalSessions +
                '}';
    }
}
