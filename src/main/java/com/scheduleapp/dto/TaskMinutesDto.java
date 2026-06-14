package com.scheduleapp.dto;

public class TaskMinutesDto {
    private Long taskId;
    private String taskTitle;
    private Integer totalMinutes;
    private String status;

    public TaskMinutesDto() {}

    public TaskMinutesDto(Long taskId, String taskTitle, Integer totalMinutes, String status) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.totalMinutes = totalMinutes;
        this.status = status;
    }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public String getTaskTitle() { return taskTitle; }
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }
    public Integer getTotalMinutes() { return totalMinutes; }
    public void setTotalMinutes(Integer totalMinutes) { this.totalMinutes = totalMinutes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
