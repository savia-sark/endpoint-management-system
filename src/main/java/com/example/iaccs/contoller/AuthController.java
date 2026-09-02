package com.example.iaccs.contoller;

import com.example.iaccs.dto.AuthRequestDTO;
import com.example.iaccs.dto.AuthResponse;
import com.example.iaccs.dto.ChangePasswordRequestDTO;
import com.example.iaccs.dto.ResetPasswordRequestDTO;
import com.example.iaccs.entity.User;
import com.example.iaccs.service.JwtService;
import com.example.iaccs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO user) {
        System.out.println("Username "+user.getUsername());
        System.out.println("Password "+user.getPassword());
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                user.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());

            AuthResponse response = new AuthResponse(
                    token,
                    "Bearer",
                    jwtService.getJwtExpiration()
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO request) {

        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO request) {

        userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}
