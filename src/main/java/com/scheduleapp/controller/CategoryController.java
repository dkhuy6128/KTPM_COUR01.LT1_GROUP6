package com.scheduleapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.model.Category;
import com.scheduleapp.model.User;
import com.scheduleapp.service.CategoryService;
import com.scheduleapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // CREATE
    @PostMapping("/{userId}")
    public ResponseEntity<?> createCategory(@PathVariable Long userId, @RequestBody Category category) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        Category createdCategory = categoryService.createCategory(category, userOpt.get());
        return ResponseEntity.ok(createdCategory);
    }

    // READ - Get all categories
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllCategories(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        List<Category> categories = categoryService.getCategoriesByUser(userOpt.get());
        return ResponseEntity.ok(categories);
    }

    // READ - Get category by ID
    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long userId, @PathVariable Long categoryId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId, userOpt.get());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoryOpt.get());
    }

    // UPDATE
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryDetails) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId, categoryDetails.getUser());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Category category = categoryOpt.get();
        category.setName(categoryDetails.getName());
        category.setColor(categoryDetails.getColor());
        category.setDescription(categoryDetails.getDescription());

        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    // DELETE
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Đã xóa category");
    }
}
