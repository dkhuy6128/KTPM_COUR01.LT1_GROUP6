package com.scheduleapp.service;

import java.util.List;
import java.util.Optional;

import com.scheduleapp.model.PomodoroSession;
import com.scheduleapp.model.Task;

public interface PomodoroService {
    
    PomodoroSession createPomodoroSession(PomodoroSession session);
    Optional<PomodoroSession> getPomodoroSessionById(Long id);
    List<PomodoroSession> getPomodoroSessionsByTask(Task task);
    PomodoroSession updatePomodoroSession(PomodoroSession session);
    void deletePomodoroSession(Long id);
    
    // Pomodoro specific methods
    Optional<PomodoroSession> getActiveSession(Task task);
    PomodoroSession startSession(Long taskId);
    void completeSession(Long sessionId, int workMinutes);
    Long getTotalCompletedSessions(Task task);
}
