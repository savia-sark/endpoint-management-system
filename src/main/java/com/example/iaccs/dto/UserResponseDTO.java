package com.example.iaccs.dto;

import com.example.iaccs.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserResponseDTO {

    private Integer userId;

    private String username;

    private Role role;

    private Boolean enabled;

    public UserResponseDTO(Integer userId, String username, Role role, Boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
