package com.scheduleapp.dto;

import com.scheduleapp.model.User;

public class AuthResponse {
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private String message;
    private Boolean success;
    private String token;

    public AuthResponse() {}

    public AuthResponse(User user, String message, Boolean success) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.message = message;
        this.success = success;
    }

    public AuthResponse(User user, String token, String message, Boolean success) {
        this(user, message, success);
        this.token = token;
    }

    public AuthResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
