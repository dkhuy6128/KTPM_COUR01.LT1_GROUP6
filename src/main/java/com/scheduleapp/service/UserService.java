package com.scheduleapp.service;

import java.util.List;
import java.util.Optional;

import com.scheduleapp.model.User;

public interface UserService {

    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    User register(String username, String password, String fullName, String email);
    Optional<User> login(String username, String password);
    User updateUser(User user);
    void deleteUserById(Long id);
    List<User> getAllUsers();
}
