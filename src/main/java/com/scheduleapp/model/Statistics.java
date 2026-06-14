package com.scheduleapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer totalTasks = 0;
    private Integer completedTasks = 0;
    private Integer pendingTasks = 0;
    private Integer cancelledTasks = 0;

    private Integer urgentTasks = 0;
    private Integer highPriorityTasks = 0;
    private Integer mediumPriorityTasks = 0;
    private Integer lowPriorityTasks = 0;

    private Integer totalPomodoroSessions = 0;
    private Integer completedPomodoroSessions = 0;

    private Double completionRate = 0.0; // Tỷ lệ hoàn thành (%)

    private Long lastUpdated;

    public Statistics() {
        this.lastUpdated = System.currentTimeMillis();
    }

    public Statistics(User user) {
        this();
        this.user = user;
    }

    /**
     * Tính toán tỷ lệ hoàn thành
     */
    public void calculateCompletionRate() {
        if (totalTasks == 0) {
            this.completionRate = 0.0;
        } else {
            this.completionRate = (completedTasks * 100.0) / totalTasks;
        }
        this.lastUpdated = System.currentTimeMillis();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Integer completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Integer getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(Integer pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public Integer getCancelledTasks() {
        return cancelledTasks;
    }

    public void setCancelledTasks(Integer cancelledTasks) {
        this.cancelledTasks = cancelledTasks;
    }

    public Integer getUrgentTasks() {
        return urgentTasks;
    }

    public void setUrgentTasks(Integer urgentTasks) {
        this.urgentTasks = urgentTasks;
    }

    public Integer getHighPriorityTasks() {
        return highPriorityTasks;
    }

    public void setHighPriorityTasks(Integer highPriorityTasks) {
        this.highPriorityTasks = highPriorityTasks;
    }

    public Integer getMediumPriorityTasks() {
        return mediumPriorityTasks;
    }

    public void setMediumPriorityTasks(Integer mediumPriorityTasks) {
        this.mediumPriorityTasks = mediumPriorityTasks;
    }

    public Integer getLowPriorityTasks() {
        return lowPriorityTasks;
    }

    public void setLowPriorityTasks(Integer lowPriorityTasks) {
        this.lowPriorityTasks = lowPriorityTasks;
    }

    public Integer getTotalPomodoroSessions() {
        return totalPomodoroSessions;
    }

    public void setTotalPomodoroSessions(Integer totalPomodoroSessions) {
        this.totalPomodoroSessions = totalPomodoroSessions;
    }

    public Integer getCompletedPomodoroSessions() {
        return completedPomodoroSessions;
    }

    public void setCompletedPomodoroSessions(Integer completedPomodoroSessions) {
        this.completedPomodoroSessions = completedPomodoroSessions;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", totalTasks=" + totalTasks +
                ", completedTasks=" + completedTasks +
                ", completionRate=" + String.format("%.2f", completionRate) + "%" +
                '}';
    }
}
