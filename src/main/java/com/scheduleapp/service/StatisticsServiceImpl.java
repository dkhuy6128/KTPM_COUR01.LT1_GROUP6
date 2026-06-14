package com.scheduleapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.dto.TaskMinutesDto;
import com.scheduleapp.model.*;
import com.scheduleapp.repository.PomodoroSessionRepository;
import com.scheduleapp.repository.StatisticsRepository;
import com.scheduleapp.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final TaskRepository taskRepository;
    private final PomodoroSessionRepository pomodoroSessionRepository;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, TaskRepository taskRepository,
                                 PomodoroSessionRepository pomodoroSessionRepository) {
        this.statisticsRepository = statisticsRepository;
        this.taskRepository = taskRepository;
        this.pomodoroSessionRepository = pomodoroSessionRepository;
    }

    @Override
    @Transactional
    public Statistics createStatistics(User user) {
        Statistics stats = new Statistics(user);
        return statisticsRepository.save(stats);
    }

    @Override
    public Optional<Statistics> getStatisticsByUser(User user) {
        return statisticsRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Statistics updateStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    @Override
    @Transactional
    public void deleteStatistics(Long id) {
        statisticsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void recalculateStatistics(User user) {
        Statistics stats = getOrCreateStatistics(user);
        List<Task> tasks = taskRepository.findByUser(user);

        stats.setTotalTasks(tasks.size());
        stats.setCompletedTasks(0);
        stats.setPendingTasks(0);
        stats.setCancelledTasks(0);
        stats.setUrgentTasks(0);
        stats.setHighPriorityTasks(0);
        stats.setMediumPriorityTasks(0);
        stats.setLowPriorityTasks(0);

        for (Task task : tasks) {
            switch (task.getStatus()) {
                case COMPLETED -> stats.setCompletedTasks(stats.getCompletedTasks() + 1);
                case TODO, IN_PROGRESS -> stats.setPendingTasks(stats.getPendingTasks() + 1);
                case CANCELLED -> stats.setCancelledTasks(stats.getCancelledTasks() + 1);
            }
            switch (task.getPriority()) {
                case URGENT -> stats.setUrgentTasks(stats.getUrgentTasks() + 1);
                case HIGH -> stats.setHighPriorityTasks(stats.getHighPriorityTasks() + 1);
                case MEDIUM -> stats.setMediumPriorityTasks(stats.getMediumPriorityTasks() + 1);
                case LOW -> stats.setLowPriorityTasks(stats.getLowPriorityTasks() + 1);
            }
        }

        long totalPomo = pomodoroSessionRepository.countByTaskUser(user);
        long completedPomo = pomodoroSessionRepository.countByTaskUserAndIsCompletedTrue(user);
        stats.setTotalPomodoroSessions((int) totalPomo);
        stats.setCompletedPomodoroSessions((int) completedPomo);
        stats.calculateCompletionRate();
        statisticsRepository.save(stats);
    }

    @Override
    public Statistics getOrCreateStatistics(User user) {
        return getStatisticsByUser(user).orElseGet(() -> createStatistics(user));
    }

    @Override
    public List<TaskMinutesDto> getTaskMinutes(User user) {
        List<Task> tasks = taskRepository.findByUser(user);
        List<TaskMinutesDto> result = new ArrayList<>();
        for (Task task : tasks) {
            int minutes = task.getWorkMinutes() != null ? task.getWorkMinutes() : 0;
            result.add(new TaskMinutesDto(task.getId(), task.getTitle(), minutes, task.getStatus().name()));
        }
        return result;
    }
}
