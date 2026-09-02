package com.example.iaccs.service;

import com.example.iaccs.dto.CurrentUserResponseDTO;
import com.example.iaccs.entity.User;

import com.example.iaccs.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final SecurityUtils securityUtil;

    public UserProfileService(SecurityUtils securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Transactional(readOnly = true)
    public CurrentUserResponseDTO getCurrentUser() {

        User user = securityUtil.getCurrentUser();

        CurrentUserResponseDTO response = new CurrentUserResponseDTO();

        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        switch (user.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                break;

            case COMMAND_HEAD:
                response.setCommandId(
                        user.getCommand().getCommandId());

                response.setCommandName(
                        user.getCommand().getCommandName());
                break;

            case NTN_HEAD:
                response.setCommandId(
                        user.getNtn()
                                .getCommand()
                                .getCommandId());

                response.setCommandName(
                        user.getNtn()
                                .getCommand()
                                .getCommandName());

                response.setNtnId(
                        user.getNtn().getNtnId());

                response.setNtnName(
                        user.getNtn().getNtnName());
                break;

            case AGENCY_HEAD:
                response.setCommandId(
                        user.getAgency()
                                .getNtn()
                                .getCommand()
                                .getCommandId());

                response.setCommandName(
                        user.getAgency()
                                .getNtn()
                                .getCommand()
                                .getCommandName());

                response.setNtnId(
                        user.getAgency()
                                .getNtn()
                                .getNtnId());

                response.setNtnName(
                        user.getAgency()
                                .getNtn()
                                .getNtnName());

                response.setAgencyId(
                        user.getAgency().getAgencyId());

                response.setAgencyName(
                        user.getAgency().getAgencyName());

                response.setAgencyTypeId(
                        user.getAgency()
                                .getAgencyType()
                                .getAgencyTypeId());

                response.setAgencyTypeName(
                        user.getAgency()
                                .getAgencyType()
                                .getTypeName());

                break;
        }

        return response;
    }
}
