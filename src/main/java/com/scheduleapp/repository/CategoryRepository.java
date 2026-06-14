package com.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduleapp.model.Category;
import com.scheduleapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Tìm tất cả category của user
    List<Category> findByUser(User user);
    
    // Tìm category theo user và name
    Optional<Category> findByUserAndName(User user, String name);
    
    // Tìm category theo user và ID
    Optional<Category> findByIdAndUser(Long id, User user);
}
