package com.scheduleapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.model.PomodoroSession;
import com.scheduleapp.model.Task;
import com.scheduleapp.service.PomodoroService;
import com.scheduleapp.service.TaskService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pomodoro")
public class PomodoroController {

    private final PomodoroService pomodoroService;
    private final TaskService taskService;

    public PomodoroController(PomodoroService pomodoroService, TaskService taskService) {
        this.pomodoroService = pomodoroService;
        this.taskService = taskService;
    }

    @PostMapping("/{taskId}/start")
    public ResponseEntity<?> startSession(@PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(pomodoroService.startSession(taskId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<?> getSessionsByTask(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task không tồn tại");
        }
        return ResponseEntity.ok(pomodoroService.getPomodoroSessionsByTask(taskOpt.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable Long id) {
        Optional<PomodoroSession> sessionOpt = pomodoroService.getPomodoroSessionById(id);
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionOpt.get());
    }

    @GetMapping("/task/{taskId}/active")
    public ResponseEntity<?> getActiveSession(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task không tồn tại");
        }
        Optional<PomodoroSession> sessionOpt = pomodoroService.getActiveSession(taskOpt.get());
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionOpt.get());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeSession(@PathVariable Long id, @RequestBody(required = false) Map<String, Integer> body) {
        if (pomodoroService.getPomodoroSessionById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        int minutes = (body != null && body.get("minutes") != null) ? body.get("minutes") : 25;
        pomodoroService.completeSession(id, minutes);
        return ResponseEntity.ok("Đã hoàn thành session");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable Long id, @RequestBody PomodoroSession sessionDetails) {
        Optional<PomodoroSession> sessionOpt = pomodoroService.getPomodoroSessionById(id);
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PomodoroSession session = sessionOpt.get();
        session.setWorkTimeMinutes(sessionDetails.getWorkTimeMinutes());
        session.setBreakTimeMinutes(sessionDetails.getBreakTimeMinutes());
        session.setTotalSessions(sessionDetails.getTotalSessions());
        return ResponseEntity.ok(pomodoroService.updatePomodoroSession(session));
    }

    @GetMapping("/task/{taskId}/completed-count")
    public ResponseEntity<?> getTotalCompletedSessions(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskService.getTaskById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task không tồn tại");
        }
        return ResponseEntity.ok(pomodoroService.getTotalCompletedSessions(taskOpt.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        pomodoroService.deletePomodoroSession(id);
        return ResponseEntity.ok("Đã xóa session");
    }
}
