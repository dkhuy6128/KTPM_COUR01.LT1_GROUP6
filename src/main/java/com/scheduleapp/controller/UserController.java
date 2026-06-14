package com.scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scheduleapp.config.JwtUtil;
import com.scheduleapp.dto.AuthResponse;
import com.scheduleapp.dto.LoginRequest;
import com.scheduleapp.dto.RegisterRequest;
import com.scheduleapp.model.User;
import com.scheduleapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Username không được để trống", false));
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Password không được để trống", false));
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Mật khẩu không khớp", false));
        }
        if (registerRequest.getFullName() == null || registerRequest.getFullName().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Tên người dùng không được để trống", false));
        }

        try {
            User user = userService.register(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getFullName(),
                    registerRequest.getEmail()
            );
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            return ResponseEntity.ok(new AuthResponse(user, token, "Đăng ký thành công", true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(new AuthResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Username không được để trống", false));
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Password không được để trống", false));
        }

        Optional<User> userOpt = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            return ResponseEntity.ok(new AuthResponse(user, token, "Đăng nhập thành công", true));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Username hoặc password không chính xác", false));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOpt.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(userId);
        return ResponseEntity.ok("Đã xóa người dùng");
    }
}
