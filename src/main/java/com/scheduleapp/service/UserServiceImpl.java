package com.scheduleapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduleapp.model.User;
import com.scheduleapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatisticsService statisticsService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, StatisticsService statisticsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.statisticsService = statisticsService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User register(String username, String password, String fullName, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (email != null && userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = new User(username, passwordEncoder.encode(password), fullName);
        user.setEmail(email);
        User savedUser = userRepository.save(user);
        statisticsService.createStatistics(savedUser);
        return savedUser;
    }

    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword()) && user.getIsActive()) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
