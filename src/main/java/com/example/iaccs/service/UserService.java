package com.example.iaccs.service;

import ch.qos.logback.core.boolex.Matcher;
import com.example.iaccs.dto.ChangePasswordRequestDTO;
import com.example.iaccs.dto.ResetPasswordRequestDTO;
import com.example.iaccs.dto.UserRequestDTO;
import com.example.iaccs.dto.UserResponseDTO;
import com.example.iaccs.entity.Agency;
import com.example.iaccs.entity.Command;
import com.example.iaccs.entity.Ntn;
import com.example.iaccs.entity.User;
import com.example.iaccs.entity.enums.Role;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.UserMapper;
import com.example.iaccs.repository.AgencyRepository;
import com.example.iaccs.repository.CommandRepo;
import com.example.iaccs.repository.NtnRepository;
import com.example.iaccs.repository.UserRepository;
import com.example.iaccs.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;
    private final NtnRepository ntnRepository;
    private final CommandRepo commandRepo;
    private  final PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SecurityUtils securityUtils;

    public UserService(UserRepository userRepository, AgencyRepository agencyRepository, NtnRepository ntnRepository, CommandRepo commandRepo,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.agencyRepository = agencyRepository;
        this.ntnRepository = ntnRepository;
        this.commandRepo = commandRepo;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO){
        System.out.println(userRequestDTO.getAgencyId());
        User user = userMapper.requestEntity(userRequestDTO);
        User currentUser=securityUtils.getCurrentUser();
        if (!currentUser.getRole().isSuperiorTo(userRequestDTO.getRole())) {
            throw new IllegalArgumentException(
                    "You cannot create a user with an equal or higher role.");
        }
        switch (userRequestDTO.getRole()) {

            case COMMAND_HEAD -> {
                if (userRequestDTO.getCommandId() == null)
                    throw new IllegalArgumentException("Command Id not defined.");
            }

            case NTN_HEAD -> {
                if (userRequestDTO.getNtnId() == null)
                    throw new IllegalArgumentException("NTN ID not defined.");
            }

            case AGENCY_HEAD -> {
                if (userRequestDTO.getAgencyId() == null)
                    throw new IllegalArgumentException("Agency ID not defined.");
            }

            default -> {
                // Ensure scope IDs are null for SUPER_ADMIN and ADMIN
                if (userRequestDTO.getAgencyId() != null || userRequestDTO.getNtnId() != null || userRequestDTO.getCommandId() != null)
                    throw new IllegalArgumentException("admins are configured with command/ntn/agency id.");
            }
        }

        if(userRequestDTO.getAgencyId()!=null) {
            Agency agency = agencyRepository.findById(userRequestDTO.getAgencyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + userRequestDTO.getAgencyId()));
            user.setAgency(agency);
        }
        if(userRequestDTO.getNtnId()!=null) {
            Ntn ntn= ntnRepository.findById(userRequestDTO.getNtnId())
                    .orElseThrow(() -> new ResourceNotFoundException("NTN not found with id: " + userRequestDTO.getNtnId()));
            user.setNtn(ntn);
        }
        if(userRequestDTO.getCommandId()!=null) {
            Command command = commandRepo.findById(userRequestDTO.getCommandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Command not found with id: " + userRequestDTO.getCommandId()));
            user.setCommand(command);
        }
        System.out.println(user.getPassword());
        return userMapper.responseDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> getAllUsers() {
        User currentUser = securityUtils.getCurrentUser();

        List<User> users = switch (currentUser.getRole()) {

            case SUPER_ADMIN, ADMIN -> userRepository.findByRoleIn(currentUser.getRole().manageableRoles());

            case COMMAND_HEAD -> {
                List<User> agencyHeads =
                        userRepository.findByAgency_Ntn_Command_CommandIdAndRole(
                                currentUser.getCommand().getCommandId(), Role.AGENCY_HEAD);
            List<User> ntnHeads =
                    userRepository.findByNtn_Command_CommandIdAndRole(
                            currentUser.getCommand().getCommandId(), Role.NTN_HEAD);

            List<User> result = new ArrayList<>();
            result.addAll(agencyHeads);
            result.addAll(ntnHeads);
            yield result;
        }

            case NTN_HEAD ->
                    userRepository.findByAgency_Ntn_NtnIdAndRoleIn(
                            currentUser.getNtn().getNtnId(),
                            currentUser.getRole().manageableRoles());

            case AGENCY_HEAD ->
                    userRepository.findByAgency_AgencyIdAndRoleIn(
                            currentUser.getAgency().getAgencyId(),
                            currentUser.getRole().manageableRoles());


        };

        return users.stream()
                .map(userMapper::responseDTO)
                .toList();
    }

    @Transactional
    public long removeUser(String username) {
        User currentUser = securityUtils.getCurrentUser();

        User target = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!currentUser.getRole().isSuperiorTo(target.getRole())) {
            throw new AccessDeniedException(
                    "You cannot delete this user.");
        }

        return userRepository.deleteByUsername(username);
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDTO dto) {

        User currentUser = securityUtils.getCurrentUser();

        if (!passwordEncoder.matches(dto.getCurrentPassword(),
                currentUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(currentUser);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO dto) {

        User currentUser = securityUtils.getCurrentUser();

        User target = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!currentUser.getRole().isSuperiorTo(target.getRole())) {
            throw new AccessDeniedException(
                    "You cannot reset this user's password.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        target.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(target);
    }
}
