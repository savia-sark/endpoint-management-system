package com.example.iaccs.mapper;

import com.example.iaccs.dto.CommandRequestDTO;
import com.example.iaccs.dto.CommandResponseDTO;
import com.example.iaccs.dto.UserRequestDTO;
import com.example.iaccs.dto.UserResponseDTO;
import com.example.iaccs.entity.Command;
import com.example.iaccs.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

    public User requestEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(encoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());
        return user;
    }

    public UserResponseDTO responseDTO(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                user.getEnabled()
        );
    }
}
