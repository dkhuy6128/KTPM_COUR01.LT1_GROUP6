package com.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduleapp.model.PomodoroSession;
import com.scheduleapp.model.Task;
import com.scheduleapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {

    List<PomodoroSession> findByTask(Task task);

    Optional<PomodoroSession> findByTaskAndIsActiveTrue(Task task);

    Optional<PomodoroSession> findByTaskAndIsCompletedFalse(Task task);

    Long countByTaskAndIsCompletedTrue(Task task);

    @Query("SELECT COUNT(p) FROM PomodoroSession p WHERE p.task.user = :user")
    Long countByTaskUser(@Param("user") User user);

    @Query("SELECT COUNT(p) FROM PomodoroSession p WHERE p.task.user = :user AND p.isCompleted = true")
    Long countByTaskUserAndIsCompletedTrue(@Param("user") User user);
}
