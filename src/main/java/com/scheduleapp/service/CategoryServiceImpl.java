package com.scheduleapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.model.Category;
import com.scheduleapp.model.User;
import com.scheduleapp.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category, User user) {
        category.setUser(user);
        if (category.getColor() == null) {
            category.setColor("#3B82F6"); // Blue mặc định
        }
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id, User user) {
        return categoryRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Category> getCategoriesByUser(User user) {
        return categoryRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name, User user) {
        return categoryRepository.findByUserAndName(user, name);
    }
}
