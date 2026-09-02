package com.example.iaccs.contoller;

import com.example.iaccs.dto.ChangePasswordRequestDTO;
import com.example.iaccs.dto.ResetPasswordRequestDTO;
import com.example.iaccs.dto.UserRequestDTO;
import com.example.iaccs.dto.UserResponseDTO;
import com.example.iaccs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/register")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO response = userService.saveUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/remove/{username}")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        long l=userService.removeUser(username);
        if(l==0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        return ResponseEntity.ok(l + " User(s) Deleted");
    }


}
