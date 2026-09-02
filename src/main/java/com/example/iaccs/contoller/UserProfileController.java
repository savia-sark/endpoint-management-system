package com.example.iaccs.contoller;

import com.example.iaccs.dto.CurrentUserResponseDTO;
import com.example.iaccs.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> me() {
        return ResponseEntity.ok(
                userProfileService.getCurrentUser());
    }
}
