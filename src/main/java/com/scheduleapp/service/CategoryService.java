package com.scheduleapp.service;

import java.util.List;
import java.util.Optional;

import com.scheduleapp.model.Category;
import com.scheduleapp.model.User;

public interface CategoryService {
    
    Category createCategory(Category category, User user);
    Optional<Category> getCategoryById(Long id, User user);
    List<Category> getCategoriesByUser(User user);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
    Optional<Category> getCategoryByName(String name, User user);
}
