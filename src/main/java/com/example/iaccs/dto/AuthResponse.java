package com.example.iaccs.dto;

public record AuthResponse(
        String token,
        String tokenType,

        long expiresIn
) {}
