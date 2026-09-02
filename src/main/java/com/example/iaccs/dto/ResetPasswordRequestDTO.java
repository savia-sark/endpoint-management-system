package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
