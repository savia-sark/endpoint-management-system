package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
