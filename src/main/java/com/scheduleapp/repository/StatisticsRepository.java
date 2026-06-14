package com.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduleapp.model.Statistics;
import com.scheduleapp.model.User;

import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    
    // Tìm statistics của user
    Optional<Statistics> findByUser(User user);
}
