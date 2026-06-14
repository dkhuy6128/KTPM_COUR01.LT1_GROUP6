package com.scheduleapp.service;

import java.util.List;
import java.util.Optional;

import com.scheduleapp.dto.TaskMinutesDto;
import com.scheduleapp.model.Statistics;
import com.scheduleapp.model.User;

public interface StatisticsService {
    
    Statistics createStatistics(User user);
    Optional<Statistics> getStatisticsByUser(User user);
    Statistics updateStatistics(Statistics statistics);
    void deleteStatistics(Long id);
    void recalculateStatistics(User user);
    Statistics getOrCreateStatistics(User user);
    List<TaskMinutesDto> getTaskMinutes(User user);
}
