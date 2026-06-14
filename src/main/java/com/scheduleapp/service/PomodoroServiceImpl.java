package com.scheduleapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.model.PomodoroSession;
import com.scheduleapp.model.Task;
import com.scheduleapp.repository.PomodoroSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    private final PomodoroSessionRepository pomodoroSessionRepository;
    private final TaskService taskService;

    public PomodoroServiceImpl(PomodoroSessionRepository pomodoroSessionRepository, TaskService taskService) {
        this.pomodoroSessionRepository = pomodoroSessionRepository;
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public PomodoroSession createPomodoroSession(PomodoroSession session) {
        return pomodoroSessionRepository.save(session);
    }

    @Override
    public Optional<PomodoroSession> getPomodoroSessionById(Long id) {
        return pomodoroSessionRepository.findById(id);
    }

    @Override
    public List<PomodoroSession> getPomodoroSessionsByTask(Task task) {
        return pomodoroSessionRepository.findByTask(task);
    }

    @Override
    @Transactional
    public PomodoroSession updatePomodoroSession(PomodoroSession session) {
        return pomodoroSessionRepository.save(session);
    }

    @Override
    @Transactional
    public void deletePomodoroSession(Long id) {
        pomodoroSessionRepository.deleteById(id);
    }

    @Override
    public Optional<PomodoroSession> getActiveSession(Task task) {
        return pomodoroSessionRepository.findByTaskAndIsActiveTrue(task);
    }

    @Override
    @Transactional
    public PomodoroSession startSession(Long taskId) {
        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new RuntimeException("Task không tồn tại"));

        Optional<PomodoroSession> activeSession = getActiveSession(task);
        if (activeSession.isPresent()) {
            return activeSession.get();
        }

        PomodoroSession session = new PomodoroSession(task, 25, 5, 1);
        session.setIsActive(true);
        session.setStartedAt(LocalDateTime.now());
        return pomodoroSessionRepository.save(session);
    }

    @Override
    @Transactional
    public void completeSession(Long sessionId, int workMinutes) {
        PomodoroSession session = pomodoroSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session không tồn tại"));

        session.setIsActive(false);
        session.setIsCompleted(true);
        session.setCompletedAt(LocalDateTime.now());
        session.setCompletedSessions(session.getCompletedSessions() + 1);
        pomodoroSessionRepository.save(session);

        if (workMinutes > 0) {
            taskService.addWorkMinutes(session.getTask().getId(), workMinutes);
        }
    }

    @Override
    public Long getTotalCompletedSessions(Task task) {
        return pomodoroSessionRepository.countByTaskAndIsCompletedTrue(task);
    }
}
