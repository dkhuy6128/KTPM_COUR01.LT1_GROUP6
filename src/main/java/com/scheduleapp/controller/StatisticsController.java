package com.scheduleapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.model.Statistics;
import com.scheduleapp.model.User;
import com.scheduleapp.service.StatisticsService;
import com.scheduleapp.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final UserService userService;

    public StatisticsController(StatisticsService statisticsService, UserService userService) {
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getStatisticsByUser(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }
        Optional<Statistics> statsOpt = statisticsService.getStatisticsByUser(userOpt.get());
        if (statsOpt.isEmpty()) {
            return ResponseEntity.ok(statisticsService.createStatistics(userOpt.get()));
        }
        return ResponseEntity.ok(statsOpt.get());
    }

    @GetMapping("/{userId}/task-minutes")
    public ResponseEntity<?> getTaskMinutes(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }
        return ResponseEntity.ok(statisticsService.getTaskMinutes(userOpt.get()));
    }

    @PutMapping("/{userId}/recalculate")
    public ResponseEntity<?> recalculateStatistics(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }
        statisticsService.recalculateStatistics(userOpt.get());
        return ResponseEntity.ok(statisticsService.getStatisticsByUser(userOpt.get()).orElse(null));
    }

    @GetMapping("/{userId}/completion-rate")
    public ResponseEntity<?> getCompletionRate(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }
        Optional<Statistics> statsOpt = statisticsService.getStatisticsByUser(userOpt.get());
        if (statsOpt.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }
        return ResponseEntity.ok(statsOpt.get().getCompletionRate());
    }
}
